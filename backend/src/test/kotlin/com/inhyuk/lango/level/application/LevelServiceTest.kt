package com.inhyuk.lango.level.application

import com.inhyuk.lango.level.dto.AssessmentResponse
import com.inhyuk.lango.level.infrastructure.UserLevelRepository
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.domain.User
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import tools.jackson.databind.ObjectMapper

class LevelServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val promptManager = mockk<PromptManager>()
    val chatModel = mockk<ChatModel>()
    val objectMapper = mockk<ObjectMapper>()
    val userLevelRepository = mockk<UserLevelRepository>()
    
    val levelService = LevelService(userRepository, promptManager, chatModel, objectMapper, userLevelRepository)

    given("An initial assessment request") {
        val email = "test@example.com"
        val user = User(email, "pw", "nick")
        
        `when`("assessing user answers") {
            every { userRepository.findByEmail(email) } returns user
            every { promptManager.getLevelAssessmentPrompt("My answers") } returns "prompt"
            every { chatModel.chat("prompt") } returns "{}"
            
            val assessmentResponse = AssessmentResponse("Advanced", "Good vocabs")
            every { objectMapper.readValue("{}", AssessmentResponse::class.java) } returns assessmentResponse

            then("it should update user level and return result") {
                val response = levelService.assessInitialLevel(email, "My answers")
                
                response.level shouldBe "Advanced"
            }
        }
    }
})
