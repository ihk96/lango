package com.inhyuk.lango.level.application

import com.inhyuk.lango.level.domain.CEFRLevel
import com.inhyuk.lango.level.domain.GrammarTestAnswer
import com.inhyuk.lango.level.domain.GrammarTestQuestion
import com.inhyuk.lango.level.domain.LevelTestAnswer
import com.inhyuk.lango.level.domain.ReadingTestAnswer
import com.inhyuk.lango.level.domain.ReadingTestQuestion
import com.inhyuk.lango.level.domain.TestQuestions
import com.inhyuk.lango.level.domain.VocabularyTestAnswer
import com.inhyuk.lango.level.domain.VocabularyTestQuestion
import com.inhyuk.lango.level.domain.WritingTestAnswer
import com.inhyuk.lango.level.domain.WritingTestQuestion
import com.inhyuk.lango.level.dto.LevelTestAnswerRequest
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class LevelTestService(private val levelTestFactory: LevelTestFactory) {
    private val questionMap = ConcurrentHashMap<String, TestQuestions>()
    private val answerMap = ConcurrentHashMap<String, LevelTestAnswer>()
    private val scope = CoroutineScope(Dispatchers.IO)

    @PreDestroy
    fun cleanUp(){
        scope.cancel()
    }

    fun generateTestQuestions(userId: String) {
        if(questionMap.containsKey(userId)) throw Exception("User already has a test")
        val testQuestions = TestQuestions()
        questionMap[userId] = testQuestions

        scope.launch {
            val vocaTests = async {levelTestFactory.generateVocaTests()}
            val grammarTests = async {levelTestFactory.generateGrammarTests()}
            val readingTests = async {levelTestFactory.generateReadingTests()}
            testQuestions.vocabulary = vocaTests.await()
            testQuestions.grammar = grammarTests.await()
            testQuestions.reading = readingTests.await()


            testQuestions.writing = listOf(
                WritingTestQuestion(
                    question = "자기소개를 3-5문장으로 작성하세요.",
                    level = "A1",
                ),
                WritingTestQuestion(
                    question = "당신의 고향에 대해 100단어 정도로 설명하세요.",
                    level = "B1",
                ),
                WritingTestQuestion(
                    question = "기술 발전이 사회에 미치는 영향에 대한 의견을 200단어로 작성하세요.",
                    level = "C1",
                ),
            )
        }
    }

    fun getVocaTestsQuestions(userId: String) : List<VocabularyTestQuestion> {
        return questionMap[userId]?.vocabulary ?: emptyList()
    }

    fun getWritingTestsQuestions(userId: String) : List<WritingTestQuestion> {
        return questionMap[userId]?.writing ?: emptyList()
    }
    fun getReadingTestsQuestions(userId: String) : List<ReadingTestQuestion> {
        return questionMap[userId]?.reading ?: emptyList()
    }
    fun getGrammarTestsQuestions(userId: String) : List<GrammarTestQuestion> {
        return questionMap[userId]?.grammar ?: emptyList()
    }

    fun answerVocaTest(userId: String, idx: Int, answer: String) {
        val testQuestions = questionMap[userId] ?: throw IllegalArgumentException("User has no test questions")
        val answerEntity = VocabularyTestAnswer(
            question = testQuestions.vocabulary[idx],
            answer = answer
        )

        val answers = answerMap[userId]?.let{
            val newAnswers = LevelTestAnswer()
            answerMap[userId] = newAnswers
            newAnswers
        }
        answers!!.vocabulary.add(answerEntity)
    }
    fun answerGrammarTest(userId: String, idx: Int, answer: String) {
        val testQuestions = questionMap[userId] ?: throw IllegalArgumentException("User has no test questions")
        val answerEntity = GrammarTestAnswer(
            question = testQuestions.grammar[idx],
            answer = answer
        )
        val answers = answerMap[userId]?.let{
            val newAnswers = LevelTestAnswer()
            answerMap[userId] = newAnswers
            newAnswers
        }
        answers!!.grammar.add(answerEntity)
    }
    fun answerReadingTest(userId: String, idx: Int, answer: String) {
        val testQuestions = questionMap[userId] ?: throw IllegalArgumentException("User has no test questions")
        val answerEntity = ReadingTestAnswer(
            question = testQuestions.reading[idx],
            answer = answer
        )
        val answers = answerMap[userId]?.let{
            val newAnswers = LevelTestAnswer()
            answerMap[userId] = newAnswers
            newAnswers
        }
        answers!!.reading.add(answerEntity)
    }
    fun answerWritingTest(userId: String, idx: Int, answer: String) {
        val testQuestions = questionMap[userId] ?: throw IllegalArgumentException("User has no test questions")
        val answerEntity = WritingTestAnswer(
            question = testQuestions.writing[idx],
            answer = answer
        )
        val answers = answerMap[userId]?.let{
            val newAnswers = LevelTestAnswer()
            answerMap[userId] = newAnswers
            newAnswers
        }
        answers!!.writing.add(answerEntity)
    }

    fun getVocaTestAnswer(userId: String) : List<VocabularyTestAnswer>? {
        return answerMap[userId]?.vocabulary
    }

    fun getGrammarTestAnswer(userId: String) : List<GrammarTestAnswer>? {
        return answerMap[userId]?.grammar
    }

    fun getReadingTestAnswer(userId: String) : List<ReadingTestAnswer>? {
        return answerMap[userId]?.reading
    }

    fun getWritingTestAnswer(userId: String) : List<WritingTestAnswer>? {
        return answerMap[userId]?.writing
    }
    fun getUsersTestAnswers(userId: String) : LevelTestAnswer? {
        return answerMap[userId]
    }
}