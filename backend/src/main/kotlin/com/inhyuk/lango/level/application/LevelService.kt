package com.inhyuk.lango.level.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inhyuk.lango.level.dto.AssessmentResponse
import com.inhyuk.lango.level.dto.QuestionResponse
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LevelService(
    private val userRepository: UserRepository,
    private val promptManager: PromptManager,
    private val chatModel: ChatLanguageModel,
    private val objectMapper: ObjectMapper
) {
    fun getInitialQuestions(): List<QuestionResponse> {
        return listOf(
            QuestionResponse(1, "What are your hobbies? Describe them in detail."),
            QuestionResponse(2, "Describe your most memorable travel experience."),
            QuestionResponse(3, "What do you think about the future of AI?")
        )
    }

    @Transactional
    fun assessInitialLevel(email: String, answers: String): AssessmentResponse {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User not found")
            
        val prompt = promptManager.getLevelAssessmentPrompt(answers)
        val responseStr = chatModel.generate(prompt)
        
        val response = objectMapper.readValue(responseStr, AssessmentResponse::class.java)
        
        user.currentLevel = response.level
        // User entity variable 'currentLevel' is mutable, dirty checking will save it.
        // However, I should make sure User.currentLevel is var. (It is)
        
        return response
    }
}
