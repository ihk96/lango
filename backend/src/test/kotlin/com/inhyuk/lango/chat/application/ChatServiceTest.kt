package com.inhyuk.lango.chat.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inhyuk.lango.chat.domain.ChatSession
import com.inhyuk.lango.chat.dto.ScenarioGenerationResponse
import com.inhyuk.lango.chat.infrastructure.ChatMessageRepository
import com.inhyuk.lango.chat.infrastructure.ChatSessionRepository
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.domain.User
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.chat.StreamingChatLanguageModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ChatServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val chatSessionRepository = mockk<ChatSessionRepository>()
    val chatMessageRepository = mockk<ChatMessageRepository>()
    val promptManager = mockk<PromptManager>()
    val chatModel = mockk<ChatLanguageModel>()
    val streamingChatModel = mockk<StreamingChatLanguageModel>()
    val objectMapper = mockk<ObjectMapper>()
    
    val chatService = ChatService(
        userRepository, chatSessionRepository, chatMessageRepository, 
        promptManager, chatModel, streamingChatModel, objectMapper
    )
    
    val feedbackService = FeedbackService(chatModel, promptManager)

    given("A scenario generation request") {
        val email = "test@example.com"
        val user = User(email, "pw", "nick", currentLevel = "Beginner")
        
        `when`("starting a session") {
            every { userRepository.findByEmail(email) } returns user
            every { promptManager.getScenarioGenerationPrompt(any(), any()) } returns "prompt"
            every { chatModel.generate("prompt") } returns "{}"
            
            val scenarioResponse = ScenarioGenerationResponse("Scenario", "Waiter", "Hello")
            every { objectMapper.readValue("{}", ScenarioGenerationResponse::class.java) } returns scenarioResponse
            
            every { chatSessionRepository.save(any()) } answers { firstArg<ChatSession>().apply { 
                // Reflection to set ID if needed, or just return as is
            } }
            every { chatMessageRepository.save(any()) } returns mockk()

            then("it should create a session and save initial message") {
                val response = chatService.startSession(email, null)
                
                response.initialMessage shouldBe "Hello"
                response.aiRole shouldBe "Waiter"
                
                verify(exactly = 1) { chatSessionRepository.save(any()) }
                verify(exactly = 1) { chatMessageRepository.save(any()) }
            }
        }
    }
    
    given("A feedback request") {
        `when`("requesting feedback") {
            every { promptManager.getFeedbackPrompt(any(), any()) } returns "feedback prompt"
            every { chatModel.generate("feedback prompt") } returns "Good grammar."
            
            then("it should return LLM response") {
                val result = feedbackService.getFeedback("Hello", "Context")
                result shouldBe "Good grammar."
            }
        }
    }
})
