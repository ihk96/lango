package com.inhyuk.lango.user.presentation

import com.inhyuk.lango.common.dto.ApiResponse
import com.inhyuk.lango.user.application.AuthService
import com.inhyuk.lango.user.dto.LoginRequest
import com.inhyuk.lango.user.dto.SignupRequest
import com.inhyuk.lango.user.dto.UserResponse
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class UserController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid request: SignupRequest): ResponseEntity<ApiResponse<UserResponse>> {
        return ResponseEntity.ok(ApiResponse(authService.signup(request)))
    }
    
    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest, session: HttpSession): ResponseEntity<ApiResponse<UserResponse>> {
        return ResponseEntity.ok(ApiResponse(authService.login(request, session)))
    }
    
    @PostMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<ApiResponse<Unit>> {
        session.invalidate()
        return ResponseEntity.ok(ApiResponse(null,"success"))
    }
}
