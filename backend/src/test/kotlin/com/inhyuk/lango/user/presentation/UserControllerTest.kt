package com.inhyuk.lango.user.presentation

import com.inhyuk.lango.user.application.AuthService
import com.inhyuk.lango.user.dto.LoginRequest
import com.inhyuk.lango.user.dto.SignupRequest
import com.inhyuk.lango.user.dto.UserResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import tools.jackson.databind.ObjectMapper
import java.util.UUID

class UserControllerTest : BehaviorSpec({
    val authService = mockk<AuthService>()
    val userController = UserController(authService)
    val mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    val objectMapper = ObjectMapper()

    given("A signup request") {
        val request = SignupRequest("test@test.com", "pw", "nick")
        val response = UserResponse(UUID.randomUUID().toString(), "test@test.com", "nick", null)

        `when`("service returns success") {
            every { authService.signup(any()) } returns response

            then("it should return 200 OK") {
                mockMvc.perform(post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.email").value("test@test.com"))
            }
        }
    }
    
    given("A login request") {
        val request = LoginRequest("test@test.com", "pw")
        val response = UserResponse(UUID.randomUUID().toString(), "test@test.com", "nick", null)
        
        `when`("service returns success") {
            every { authService.login(any(), any()) } returns response
            
            then("it should return 200 OK") {
                mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.success").value(true))
            }
        }
    }
})
