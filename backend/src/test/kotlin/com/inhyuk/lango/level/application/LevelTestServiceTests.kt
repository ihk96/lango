package com.inhyuk.lango.level.application

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class LevelTestServiceTests : BehaviorSpec({
    val levelTestFactory = mockk<LevelTestFactory>()
    val levelTestService = LevelTestService(levelTestFactory)

    given("A level test request") {
        val userId = "test"
        `when`("the request is valid") {
            every { levelTestFactory.generateVocaTests() } returns emptyList()
            every { levelTestFactory.generateGrammarTests() } returns emptyList()
            every { levelTestFactory.generateReadingTests() } returns emptyList()

            then("the level test should be created") {
                shouldNotThrow<Exception> {
                    levelTestService.generateTestQuestions(userId)
                }
            }
        }
    }

})