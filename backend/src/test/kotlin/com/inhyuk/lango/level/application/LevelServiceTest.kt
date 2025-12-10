package com.inhyuk.lango.level.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inhyuk.lango.level.dto.AssessmentResponse
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.domain.User
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatLanguageModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class LevelServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val promptManager = mockk<PromptManager>()
    val chatModel = mockk<ChatLanguageModel>()
    val objectMapper = mockk<ObjectMapper>()
    
    val levelService = LevelService(userRepository, promptManager, chatModel, objectMapper)

    given("An initial assessment request") {
        val email = "test@example.com"
        val user = User(email, "pw", "nick", currentLevel = "Beginner")
        
        `when`("assessing user answers") {
            every { userRepository.findByEmail(email) } returns user
            every { promptManager.getLevelAssessmentPrompt("My answers") } returns "prompt"
            every { chatModel.generate("prompt") } returns "{}"
            
            val assessmentResponse = AssessmentResponse("Advanced", "Good vocabs")
            every { objectMapper.readValue("{}", AssessmentResponse::class.java) } returns assessmentResponse
            
            then("it should update user level and return result") {
                val response = levelService.assessInitialLevel(email, "My answers")
                
                response.level shouldBe "Advanced"
                user.currentLevel shouldBe "Advanced"
            }
        }
    }
})
