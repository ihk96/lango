package com.inhyuk.lango.chat.application


import com.inhyuk.lango.chat.domain.ChatSessionEntity
import com.inhyuk.lango.chat.dto.ScenarioGenerationResponse
import com.inhyuk.lango.chat.infrastructure.ChatMessageRepository
import com.inhyuk.lango.chat.infrastructure.ChatSessionRepository
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.domain.User
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.chat.request.ChatRequest
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
    val promptManager = mockk<PromptManager>()
    val chatModel = mockk<ChatModel>()
    val streamingChatModel = mockk<StreamingChatModel>()
    val objectMapper = mockk<ObjectMapper>()

    val chatService = ChatService(
        userRepository, chatSessionRepository, chatMessageRepository,
        promptManager, chatModel, objectMapper, userLevelRepository
    )
    
    val feedbackService = FeedbackService(chatModel, promptManager)

    given("A scenario generation request") {
        val email = "test@example.com"
        val user = User(email, "pw", "nick")
        
        `when`("starting a session") {
            every { userRepository.findById(any<String>()) } returns Optional.of(user)
            every { promptManager.getScenarioGenerationPrompt(any(), any()) } returns "prompt"
            every { chatModel.chat(any<ChatRequest>()) } returns mockk(relaxed = true)
            
            val scenarioResponse = ScenarioGenerationResponse("Scenario", "Waiter", "Hello")
            every { objectMapper.readValue(any<String>(), ScenarioGenerationResponse::class.java) } returns scenarioResponse

            every { chatSessionRepository.save(any()) } answers { spyk(firstArg<ChatSessionEntity>()){
                every { id } returns 1L
            } }
            every { chatMessageRepository.save(any()) } returnsArgument 0

            then("it should create a session and save initial message") {
                val response = chatService.createSession(email, null)
                
                response.scenario shouldBe "Scenario"
                response.aiRole shouldBe "Waiter"
                response.userRole shouldBe "Hello"
                
                verify(exactly = 1) { chatSessionRepository.save(any()) }
            }
        }
    }
    
    given("A feedback request") {
        `when`("requesting feedback") {
            every { promptManager.getFeedbackPrompt(any(), any()) } returns "feedback prompt"
            every { chatModel.chat("feedback prompt") } returns "Good grammar."
            
            then("it should return LLM response") {
                val result = feedbackService.getFeedback("Hello", "Context")
                result shouldBe "Good grammar."
            }
        }
    }
})
