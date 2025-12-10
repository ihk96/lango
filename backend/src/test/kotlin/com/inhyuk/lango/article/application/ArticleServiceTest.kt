package com.inhyuk.lango.article.application

import com.inhyuk.lango.article.dto.ArticleResponse
import com.inhyuk.lango.llm.prompt.PromptManager
import com.inhyuk.lango.user.domain.User
import com.inhyuk.lango.user.infrastructure.UserRepository
import dev.langchain4j.model.chat.ChatModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import tools.jackson.databind.ObjectMapper

class ArticleServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val promptManager = mockk<PromptManager>()
    val chatModel = mockk<ChatModel>()
    val objectMapper = mockk<ObjectMapper>()
    
    val articleService = ArticleService(userRepository, promptManager, chatModel, objectMapper)

    given("An article generation request") {
        val email = "test@example.com"
        val user = User(email, "pw", "nick", currentLevel = "Advanced")
        
        `when`("user exists") {
            every { userRepository.findByEmail(email) } returns user
            every { promptManager.getArticleGenerationPrompt("Advanced", "AI") } returns "prompt"
            every { chatModel.chat("prompt") } returns "{}"
            val articleResponse = ArticleResponse("Title", "Content", emptyList())
            every { objectMapper.readValue("{}", ArticleResponse::class.java) } returns articleResponse
            
            then("it should return article response") {
                val response = articleService.generateArticle(email, "AI")
                
                response.title shouldBe "Title"
                response.content shouldBe "Content"
            }
        }
    }
})
