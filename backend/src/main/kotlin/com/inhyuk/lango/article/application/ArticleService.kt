package com.inhyuk.lango.article.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inhyuk.lango.article.dto.ArticleResponse
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val userRepository: UserRepository,
    private val promptManager: PromptManager,
    private val chatModel: ChatLanguageModel,
    private val objectMapper: ObjectMapper
) {

    @Transactional(readOnly = true)
    fun generateArticle(email: String, topic: String?): ArticleResponse {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User not found")
            
        val userLevel = user.currentLevel ?: "Beginner"
        val prompt = promptManager.getArticleGenerationPrompt(userLevel, topic)
        
        val response = chatModel.generate(prompt)
        return objectMapper.readValue(response, ArticleResponse::class.java)
    }
}
