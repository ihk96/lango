package com.inhyuk.lango.level.dto

data class VocabularyTestQuestion (
    val question: String,
    val answer: String,
    val level: String,
    val type: String
)

data class ReadingTestQuestion(
    val passageText: String,
    val question: String,
    val answer: String,
    val level: String
)

data class WritingTestQuestion(
    val question: String,
    val level: String
)

data class GrammarTestQuestion(
    val passageText: String,
    val answer: String,
    val level: String,
    val options: List<String>
)