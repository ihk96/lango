package com.inhyuk.lango.level.presentation

import com.inhyuk.lango.common.dto.ApiResponse
import com.inhyuk.lango.level.application.LevelService
import com.inhyuk.lango.level.application.LevelTestEvaluationService
import com.inhyuk.lango.level.application.LevelTestService
import com.inhyuk.lango.level.domain.GrammarTestQuestion
import com.inhyuk.lango.level.domain.ReadingTestQuestion
import com.inhyuk.lango.level.domain.VocabularyTestQuestion
import com.inhyuk.lango.level.domain.WritingTestQuestion
import com.inhyuk.lango.level.dto.LevelTestAnswerRequest
import com.inhyuk.lango.level.dto.UsersLevelResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users/levels/tests")
class UsersLevelTestController(
    private val levelTestService: LevelTestService,
    private val levelTestEvaluationService: LevelTestEvaluationService,
    private val levelService: LevelService
){
    /**
     * 사용자의 레벨 테스트 문제를 생성합니다.
     * @param authentication 인증 정보
     * @return 생성 성공 응답
     */
    @PostMapping("/generate")
    fun getUsersLevel(
        authentication: Authentication
    ) : ResponseEntity<Any>{
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        levelTestService.generateTestQuestions(userId)

        return ResponseEntity.ok().build()
    }

    /**
     * 사용자의 어휘 테스트 문제 목록을 조회합니다.
     * @param authentication 인증 정보
     * @return 어휘 테스트 문제 목록
     */
    @GetMapping("/questions/vocabulary")
    fun getUsersVocaQuestions(
        authentication: Authentication
    ) : ResponseEntity<ApiResponse<List<VocabularyTestQuestion>>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(
            ApiResponse(levelTestService.getVocaTestsQuestions(userId))
        )
    }

    /**
     * 사용자의 문법 테스트 문제 목록을 조회합니다.
     * @param authentication 인증 정보
     * @return 문법 테스트 문제 목록
     */
    @GetMapping("/questions/grammar")
    fun getUsersGrammarQuestions(
        authentication: Authentication
    ) : ResponseEntity<ApiResponse<List<GrammarTestQuestion>>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return ResponseEntity.ok(
            ApiResponse(levelTestService.getGrammarTestsQuestions(userId))
        )
    }

    /**
     * 사용자의 작문 테스트 문제 목록을 조회합니다.
     * @param authentication 인증 정보
     * @return 작문 테스트 문제 목록
     */
    @GetMapping("/questions/writing")
    fun getUsersWritingQuestions(
        authentication: Authentication
    ) : ResponseEntity<ApiResponse<List<WritingTestQuestion>>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return ResponseEntity.ok(
            ApiResponse(levelTestService.getWritingTestsQuestions(userId))
        )
    }

    /**
     * 사용자의 독해 테스트 문제 목록을 조회합니다.
     * @param authentication 인증 정보
     * @return 독해 테스트 문제 목록
     */
    @GetMapping("/questions/reading")
    fun getUsersReadingQuestions(
        authentication: Authentication
    ) : ResponseEntity<ApiResponse<List<ReadingTestQuestion>>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return ResponseEntity.ok(
            ApiResponse(levelTestService.getReadingTestsQuestions(userId))
        )
    }

    /**
     * 어휘 테스트 문제에 대한 답변을 제출합니다.
     * @param answers 답변 요청 정보
     * @param authentication 인증 정보
     * @return 제출 성공 응답
     */
    @PostMapping("/answers/vocabulary")
    fun answerVocaTest(
        @RequestBody answers: LevelTestAnswerRequest,
        authentication: Authentication
    ) : ResponseEntity<Unit> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        levelTestService.answerVocaTest(
            userId = userId,
            idx = answers.questionId,
            answer = answers.answer
        )
        return ResponseEntity.ok().build()
    }

    /**
     * 문법 테스트 문제에 대한 답변을 제출합니다.
     * @param answers 답변 요청 정보
     * @param authentication 인증 정보
     * @return 제출 성공 응답
     */
    @PostMapping("/answers/grammar")
    fun answerGrammarTest(
        @RequestBody answers: LevelTestAnswerRequest,
        authentication: Authentication
    ) : ResponseEntity<Unit> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        levelTestService.answerGrammarTest(
            userId = userId,
            idx = answers.questionId,
            answer = answers.answer
        )
        return ResponseEntity.ok().build()
    }

    /**
     * 작문 테스트 문제에 대한 답변을 제출합니다.
     * @param answers 답변 요청 정보
     * @param authentication 인증 정보
     * @return 제출 성공 응답
     */
    @PostMapping("/answers/writing")
    fun answerWritingTest(
        @RequestBody answers: LevelTestAnswerRequest,
        authentication: Authentication
    ) : ResponseEntity<Unit> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        levelTestService.answerWritingTest(
            userId = userId,
            idx = answers.questionId,
            answer = answers.answer
        )
        return ResponseEntity.ok().build()
    }

    /**
     * 독해 테스트 문제에 대한 답변을 제출합니다.
     * @param answers 답변 요청 정보
     * @param authentication 인증 정보
     * @return 제출 성공 응답
     */
    @PostMapping("/answers/reading")
    fun answerReadingTest(
        @RequestBody answers: LevelTestAnswerRequest,
        authentication: Authentication
    ) : ResponseEntity<Unit> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        levelTestService.answerReadingTest(
            userId = userId,
            idx = answers.questionId,
            answer = answers.answer
        )
        return ResponseEntity.ok().build()
    }

    /**
     * 모든 테스트 답변을 평가하고 사용자의 레벨을 저장합니다.
     * @param authentication 인증 정보
     * @return 평가된 레벨 정보
     */
    @PostMapping("/evaluate")
    fun evaluateTest(
        authentication: Authentication
    ) : ResponseEntity<ApiResponse<String>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val answers = levelTestService.getUsersTestAnswers(userId) ?: throw IllegalArgumentException()
        val level = levelTestEvaluationService.evaluate(answers)
        levelService.saveUserLevel(userId, level)
        
        return ResponseEntity.ok(ApiResponse(level.level))
    }
}