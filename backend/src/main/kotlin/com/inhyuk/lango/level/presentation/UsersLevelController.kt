package com.inhyuk.lango.level.presentation

import com.inhyuk.lango.common.dto.ApiResponse
import com.inhyuk.lango.level.application.LevelService
import com.inhyuk.lango.level.dto.UsersLevelResponse
import com.inhyuk.lango.level.dto.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/levels")
class UsersLevelController(
    private val levelService: LevelService
) {

    @GetMapping
    fun getUsersLevel(
        authentication: Authentication
    ) : ResponseEntity<ApiResponse<UsersLevelResponse>> {
        val userId = authentication.principal as? String ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        val userLevel = levelService.getUserslevel(userId)
        return ResponseEntity.ok(ApiResponse(userLevel.toResponse()))
    }
}