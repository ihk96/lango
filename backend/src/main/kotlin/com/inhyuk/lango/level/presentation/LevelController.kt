package com.inhyuk.lango.level.presentation

import com.inhyuk.lango.common.dto.ApiResponse
import com.inhyuk.lango.level.application.LevelService
import com.inhyuk.lango.level.dto.AssessmentRequest
import com.inhyuk.lango.level.dto.AssessmentResponse
import com.inhyuk.lango.level.dto.QuestionResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/level")
class LevelController(
    private val levelService: LevelService
) {

    @GetMapping("/initial-questions")
    fun getInitialQuestions(): ResponseEntity<ApiResponse<List<QuestionResponse>>> {
        return ResponseEntity.ok(ApiResponse(levelService.getInitialQuestions()))
    }

    @PostMapping("/assess-initial")
    fun assessInitialLevel(
        @RequestBody request: AssessmentRequest,
        principal: Principal
    ): ResponseEntity<ApiResponse<AssessmentResponse>> {
        return ResponseEntity.ok(ApiResponse(levelService.assessInitialLevel(principal.name, request.answers)))
    }
}
