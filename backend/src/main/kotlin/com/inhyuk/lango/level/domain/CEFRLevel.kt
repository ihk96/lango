package com.inhyuk.lango.level.domain

data class CEFRLevel(
    val level: String,
    val vocabulary: CEFRVocabularyCount,
    val grammar: CEFRGrammar,
    val reading: CEFRReading,
    val writing: CEFRWriting,
    val interaction: CEFRInteraction
)

data class IntRange(
    val min: Int,
    val max: Int
)

data class CEFRReading(
    val wordCountRange: IntRange,
    val sentenceLengthRange: IntRange,
    val complexity: String
)

data class CEFRWriting(
    val expectedLengthRange: IntRange,
    val sentenceCountRange: IntRange,
    val errorTolerance: Double
)

data class CEFRInteraction(
    val responseStyle: String,
    val feedbackDetail: String,
    val exampleRequired: Boolean
)

data class CEFRVocabularyCount(
    val commonWords: Int,
    val max: Int
)

data class CEFRGrammar(
    val allowed: List<String>,
    val forbidden: List<String>
)
