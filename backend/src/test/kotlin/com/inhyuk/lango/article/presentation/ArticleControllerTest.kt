package com.inhyuk.lango.article.presentation

import com.inhyuk.lango.article.application.ArticleService
import com.inhyuk.lango.article.dto.ArticleGenerationRequest
import com.inhyuk.lango.article.dto.ArticleResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

class ArticleControllerTest : BehaviorSpec({
    val articleService = mockk<ArticleService>()
    val articleController = ArticleController(articleService)
    val mockMvc = MockMvcBuilders.standaloneSetup(articleController).build()
    val objectMapper = ObjectMapper()

    given("An article generation request") {
        val request = ArticleGenerationRequest("AI")
        
        `when`("service generates article") {
            every { articleService.generateArticle(any(), any()) } returns 
                ArticleResponse("Title", "Content", emptyList())

            then("it should return 200 OK") {
                mockMvc.perform(post("/api/articles/generate")
                    .principal { "test@test.com" }
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk)
            }
        }
    }
})
