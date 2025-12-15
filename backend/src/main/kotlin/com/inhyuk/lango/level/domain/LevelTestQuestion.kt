package com.inhyuk.lango.level.domain

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

data class TestQuestions(
    var vocabulary: List<VocabularyTestQuestion> = emptyList(),
    var grammar: List<GrammarTestQuestion> = emptyList(),
    var reading: List<ReadingTestQuestion> = emptyList(),
    var writing: List<WritingTestQuestion> = emptyList()
)