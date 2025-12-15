package com.inhyuk.lango.level.dto

import com.inhyuk.lango.level.domain.TestQuestions

data class LevelTestAnswerRequest(
    val questionId: Int,
    val answer: String
)