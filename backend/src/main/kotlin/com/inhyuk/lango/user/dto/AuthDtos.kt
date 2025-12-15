package com.inhyuk.lango.user.dto

import com.inhyuk.lango.user.domain.UserEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignupRequest(
    @field:Email
    @field:NotBlank
    val email: String,
    
    @field:NotBlank
    val password: String,
    
    @field:NotBlank
    val nickname: String
)

data class LoginRequest(
    @field:Email
    @field:NotBlank
    val email: String,
    
    @field:NotBlank
    val password: String
)

data class UserResponse(
    val id: String,
    val email: String,
    val nickname: String,
) {
    companion object {
        fun from(user: UserEntity): UserResponse {
            return UserResponse(
                id = user.id!!,
                email = user.email,
                nickname = user.nickname,
            )
        }
    }
}
