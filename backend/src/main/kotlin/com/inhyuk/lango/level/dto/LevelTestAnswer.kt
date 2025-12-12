package com.inhyuk.lango.level.dto

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
    val vocabulary: List<VocabularyTestAnswer>,
    val grammar: List<GrammarTestAnswer>,
    val reading: List<ReadingTestAnswer>,
    val writing: List<WritingTestAnswer>
)