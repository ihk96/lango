package com.inhyuk.lango.level.application

import com.inhyuk.lango.ModelConfigForTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import tools.jackson.databind.ObjectMapper

class LevelTestFactoryTests : BehaviorSpec({

    val chatModel = ModelConfigForTest.chatModel

    val levelTestFactory = LevelTestFactory(
        chatModel,
        ObjectMapper()
    )

    given("Level test factory") {
        `when`("generating voca test") {
            val vocaTest = levelTestFactory.generateVocaTests()
            then("it should return a list of questions") {
                vocaTest.forEach { println(it) }
                vocaTest.size shouldBe 10
            }
        }

        `when`("generating grammar test") {
            val grammarTest = levelTestFactory.generateGrammarTests()
            then("it should return a list of questions") {
                grammarTest.forEach { println(it) }
                grammarTest.size shouldBe 15
            }
        }
        `when`("generating reading test") {
            val readingTest = levelTestFactory.generateReadingTests()
            then("it should return a list of questions") {
                readingTest.forEach { println(it) }
                readingTest.size shouldBe 5
            }
        }
    }

})