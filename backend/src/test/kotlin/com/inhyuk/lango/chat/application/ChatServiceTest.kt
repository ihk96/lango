package com.inhyuk.lango.chat.application


import com.inhyuk.lango.chat.domain.ChatSessionEntity
import com.inhyuk.lango.chat.domain.ScenarioChatMessage
import com.inhyuk.lango.chat.dto.ScenarioGenerationResponse
import com.inhyuk.lango.chat.infrastructure.ChatMessageRepository
import com.inhyuk.lango.chat.infrastructure.ChatSessionRepository
import com.inhyuk.lango.level.domain.UserLevelEntity
import com.inhyuk.lango.level.infrastructure.UserLevelRepository
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.domain.UserEntity
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import tools.jackson.databind.ObjectMapper
import java.util.Optional

class ChatServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val chatSessionRepository = mockk<ChatSessionRepository>()
    val chatMessageRepository = mockk<ChatMessageRepository>()
    val chatModel = mockk<ChatModel>()
    val objectMapper = mockk<ObjectMapper>()
    val userLevelRepository = mockk<UserLevelRepository>()

    val chatService = ChatService(
        userRepository, chatSessionRepository, chatMessageRepository,
        chatModel, objectMapper, userLevelRepository
    )
    val email = "test@example.com"
    val userId = "userId"
    val user = UserEntity(email, "pw", "nick")

    given("정상적인 get 요청") {
        every { chatSessionRepository.findByUserIdOrderByCreatedAtDesc(any()) } returns listOf()
        every { chatSessionRepository.findById(any()) } returns mockk(relaxed = true)
        every { chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(any()) } returns mockk(relaxed = true)
        `when`("sessions 정보를 조회하면") {
            then("에러가 발생하지 않아야 한다.") {
                shouldNotThrow<Exception> {
                    chatService.getChatSessions(userId)
                }
            }
        }
        `when`("특정 session을 조회하면") {
            then("에러가 발생하지 않아야 한다.") {
                shouldNotThrow<Exception> {
                    chatService.getChatSession("sessionId")
                }
            }
        }
        `when`("session의 history를 조회하면") {
            then("에러가 발생하지 않아야 한다.") {
                shouldNotThrow<Exception> {
                    chatService.getChatHistory(sessionId = "sessionId")
                }
            }
        }
    }


    given("토픽을 포함하여 정상적인 세션 생성") {
        val userMock = mockk<UserEntity>(relaxed = true)
        every { userRepository.findById(any<String>()) } returns Optional.of(userMock)
        every { userLevelRepository.findByUserId(any()) } returns UserLevelEntity(
            userId = "userId",
            level = "A1.1"
        )
        every { chatModel.chat(any<ChatRequest>()) } returns mockk(relaxed = true)

        val scenarioResponse = ScenarioGenerationResponse(
            "Scenario", "Waiter", "aiRole",
            userRole = "userRole"
        )
        every { objectMapper.readValue(any<String>(), ScenarioGenerationResponse::class.java) } returns scenarioResponse

        every { chatSessionRepository.save(any()) } answers { spyk(firstArg<ChatSessionEntity>()){
            every { id } returns "23"
        } }
        every { chatMessageRepository.save(any()) } returnsArgument 0

        `when`("starting a session") {
            val response = chatService.createSession(email, "커피 주문")
            then("it should create a session and save initial message") {
                verify(exactly = 1) { chatSessionRepository.save(any()) }
            }
        }
    }

    given("정상적인 세션 시작"){
        every { chatSessionRepository.findById(any()) } returns Optional.of(mockk(relaxed = true){
            every { userLevel } returns "A1.1"
        })
        every { chatModel.chat(any<ChatRequest>()) } returns mockk(relaxed = true)
        every { objectMapper.readValue(any<String>(), ScenarioChatMessage::class.java) } returns mockk(relaxed = true) {
            every { code } returns 0
        }
        every { chatMessageRepository.save(any()) } returnsArgument 0
        `when`("starting a session") {
            val response = chatService.startSessionChat("sessionId")
            then("it should return a message") {
                response.code shouldBe 0
            }
        }
    }

    given("정상적인 채팅 요청"){
        every { chatSessionRepository.findById(any()) } returns Optional.of(mockk(relaxed = true){
            every { userLevel } returns "A1.1"
        })
        every { chatModel.chat(any<ChatRequest>()) } returns mockk(relaxed = true)
        every { objectMapper.readValue(any<String>(), ScenarioChatMessage::class.java) } returns mockk(relaxed = true) {
            every { code } returns 0
        }
        every { chatMessageRepository.save(any()) } returnsArgument 0
        every { chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(any()) } returns mockk(relaxed = true)
        `when`("starting a session") {
            val response = chatService.chatMessage("userId", "sessionId", "usermessage")
            then("it should return a message") {
                response.code shouldBe 0
            }
        }
    }

    
})
