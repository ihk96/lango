package com.inhyuk.lango.level.presentation

import com.inhyuk.lango.level.application.LevelService
import com.inhyuk.lango.level.application.LevelTestEvaluationService
import com.inhyuk.lango.level.application.LevelTestService
import com.inhyuk.lango.level.domain.*
import com.inhyuk.lango.level.dto.LevelTestAnswerRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.security.core.Authentication
import tools.jackson.databind.ObjectMapper

class UsersLevelTestControllerTest : BehaviorSpec({
    val levelTestService = mockk<LevelTestService>()
    val levelTestEvaluationService = mockk<LevelTestEvaluationService>()
    val levelService = mockk<LevelService>()

    val controller = UsersLevelTestController(
        levelTestService,
        levelTestEvaluationService,
        levelService
    )

    val authentication = mockk<Authentication>()
    every { authentication.principal } returns "user1"
    every { authentication.name } returns "user1"

    val mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .build()
    val objectMapper = ObjectMapper()

    given("레벨 테스트 문제 생성") {
        `when`("서비스가 생성 작업을 수행") {
            every { levelTestService.generateTestQuestions(any()) } returns Unit

            then("200 OK") {
                mockMvc.perform(post("/api/v1/users/levels/tests/generate")
                    .principal(authentication))
                    .andExpect(status().isOk)
            }
        }
    }

    given("어휘 문제 조회") {
        val items = listOf(
            VocabularyTestQuestion(
                question = "Q1",
                answer = "A1",
                level = "A1.1",
                type = "WORD"
            )
        )
        `when`("서비스가 목록을 반환") {
            every { levelTestService.getVocaTestsQuestions(any()) } returns items

            then("200 OK 와 데이터 배열 반환") {
                mockMvc.perform(get("/api/v1/users/levels/tests/questions/vocabulary").principal(authentication))

                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data[0].question").value("Q1"))
            }
        }
    }

    given("문법 문제 조회") {
        val items = listOf(
            GrammarTestQuestion(
                passageText = "P",
                answer = "A",
                level = "A1.1",
                options = listOf("a", "b")
            )
        )
        `when`("서비스가 목록을 반환") {
            every { levelTestService.getGrammarTestsQuestions(any()) } returns items

            then("200 OK") {
                mockMvc.perform(get("/api/v1/users/levels/tests/questions/grammar").principal(authentication))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data[0].answer").value("A"))
            }
        }
    }

    given("작문 문제 조회") {
        val items = listOf(
            WritingTestQuestion(
                question = "Write this",
                level = "A1.1"
            )
        )
        `when`("서비스가 목록을 반환") {
            every { levelTestService.getWritingTestsQuestions(any()) } returns items

            then("200 OK") {
                mockMvc.perform(get("/api/v1/users/levels/tests/questions/writing").principal(authentication))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data[0].question").value("Write this"))
            }
        }
    }

    given("독해 문제 조회") {
        val items = listOf(
            ReadingTestQuestion(
                passageText = "passage",
                question = "Q",
                answer = "A",
                level = "A1.1"
            )
        )
        `when`("서비스가 목록을 반환") {
            every { levelTestService.getReadingTestsQuestions(any()) } returns items

            then("200 OK") {
                mockMvc.perform(get("/api/v1/users/levels/tests/questions/reading").principal(authentication))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data[0].question").value("Q"))
            }
        }
    }

    given("어휘 답변 제출") {
        val req = LevelTestAnswerRequest(0, "ans")
        `when`("서비스가 저장") {
            every { levelTestService.answerVocaTest(any(), any(), any()) } returns Unit

            then("200 OK") {
                mockMvc.perform(
                    post("/api/v1/users/levels/tests/answers/vocabulary")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                ).andExpect(status().isOk)
            }
        }
    }

    given("문법 답변 제출") {
        val req = LevelTestAnswerRequest(0, "ans")
        `when`("서비스가 저장") {
            every { levelTestService.answerGrammarTest(any(), any(), any()) } returns Unit

            then("200 OK") {
                mockMvc.perform(
                    post("/api/v1/users/levels/tests/answers/grammar")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                ).andExpect(status().isOk)
            }
        }
    }

    given("작문 답변 제출") {
        val req = LevelTestAnswerRequest(0, "ans")
        `when`("서비스가 저장") {
            every { levelTestService.answerWritingTest(any(), any(), any()) } returns Unit

            then("200 OK") {
                mockMvc.perform(
                    post("/api/v1/users/levels/tests/answers/writing")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                ).andExpect(status().isOk)
            }
        }
    }

    given("독해 답변 제출") {
        val req = LevelTestAnswerRequest(0, "ans")
        `when`("서비스가 저장") {
            every { levelTestService.answerReadingTest(any(), any(), any()) } returns Unit

            then("200 OK") {
                mockMvc.perform(
                    post("/api/v1/users/levels/tests/answers/reading")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                ).andExpect(status().isOk)
            }
        }
    }

    given("테스트 평가") {
        val answers = LevelTestAnswer()
        val level = Levels.findLevel("A1.1")!!
        `when`("서비스가 평가 및 저장") {
            every { levelTestService.getUsersTestAnswers(any()) } returns answers
            every { levelTestEvaluationService.evaluate(answers) } returns level
            every { levelService.saveUserLevel(any(), any<CEFRLevel>()) } returns mockk(relaxed = true)

            then("200 OK 와 레벨 문자열 반환") {
                mockMvc.perform(post("/api/v1/users/levels/tests/evaluate").principal(authentication))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.data").value("A1.1"))
            }
        }
    }
})
