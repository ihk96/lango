package com.inhyuk.lango.level.dto

import com.inhyuk.lango.level.domain.CEFRLevel
import com.inhyuk.lango.level.domain.Levels
import com.inhyuk.lango.level.domain.UserLevelEntity
import java.time.LocalDateTime

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

data class UsersLevelResponse(
    val id : String,
    val userId: String,
    val level : CEFRLevel,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
fun UserLevelEntity.toResponse() = UsersLevelResponse(
    id = id!!,
    userId = userId,
    level = Levels.findLevel(level) ?: throw IllegalStateException(),
    createdAt = createdAt,
    updatedAt = updatedAt
)