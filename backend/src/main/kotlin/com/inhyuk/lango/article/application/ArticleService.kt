package com.inhyuk.lango.article.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inhyuk.lango.article.dto.ArticleResponse
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val userRepository: UserRepository,
    private val promptManager: PromptManager,
    private val chatModel: ChatModel,
    private val objectMapper: tools.jackson.databind.ObjectMapper
) {

    @Transactional(readOnly = true)
    fun generateArticle(email: String, topic: String?): ArticleResponse {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User not found")
            
        val userLevel = user.currentLevel ?: "Beginner"
        val prompt = promptManager.getArticleGenerationPrompt(userLevel, topic)
        
        val response = chatModel.chat(prompt)
        return objectMapper.readValue(response, ArticleResponse::class.java)
    }
}
