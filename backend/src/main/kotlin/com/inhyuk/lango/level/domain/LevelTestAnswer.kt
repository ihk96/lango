package com.inhyuk.lango.level.domain

data class VocabularyTestAnswer(
    val question: VocabularyTestQuestion,
    val answer: String
)
data class GrammarTestAnswer(
    val question: GrammarTestQuestion,
    val answer: String
)
data class ReadingTestAnswer(
    val question: ReadingTestQuestion,
    val answer: String
)
data class WritingTestAnswer(
    val question: WritingTestQuestion,
    val answer: String
)

data class LevelTestAnswer(
    var vocabulary: MutableList<VocabularyTestAnswer> = mutableListOf(),
    var grammar: MutableList<GrammarTestAnswer> = mutableListOf(),
    var reading: MutableList<ReadingTestAnswer> = mutableListOf(),
    var writing: MutableList<WritingTestAnswer> = mutableListOf()
)