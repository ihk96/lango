package com.inhyuk.lango.level.dto

data class AssessmentRequest(
    val answers: String
)

data class AssessmentResponse(
    val level: String,
    val reason: String
)

data class QuestionResponse(
    val id: Int,
    val question: String
)
