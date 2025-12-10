package com.inhyuk.lango.article.presentation

import com.inhyuk.lango.article.application.ArticleService
import com.inhyuk.lango.article.dto.ArticleGenerationRequest
import com.inhyuk.lango.article.dto.ArticleResponse
import com.inhyuk.lango.common.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    private val articleService: ArticleService
) {

    @PostMapping("/generate")
    fun generateArticle(
        @RequestBody request: ArticleGenerationRequest,
        principal: Principal
    ): ResponseEntity<ApiResponse<ArticleResponse>> {
        return ResponseEntity.ok(ApiResponse(articleService.generateArticle(principal.name, request.topic)))
    }
}
