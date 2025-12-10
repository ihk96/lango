package com.inhyuk.lango.article.dto

data class ArticleGenerationRequest(
    val topic: String?
)

data class ArticleResponse(
    val title: String,
    val content: String,
    val vocabulary: List<VocabularyItem>
)

data class VocabularyItem(
    val word: String,
    val meaning: String
)
