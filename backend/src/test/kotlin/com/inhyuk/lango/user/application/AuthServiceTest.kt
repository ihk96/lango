package com.inhyuk.lango.user.application

import com.inhyuk.lango.user.domain.UserEntity
import com.inhyuk.lango.user.dto.LoginRequest
import com.inhyuk.lango.user.dto.SignupRequest
import com.inhyuk.lango.user.infrastructure.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.mock.web.MockHttpSession
import java.util.UUID

class AuthServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val authService = AuthService(userRepository, passwordEncoder)

    given("A signup request") {
        val request = SignupRequest("test@example.com", "password", "testuser")
        val user = UserEntity("test@example.com", "encodedPw", "testuser")
        val userId = UUID.randomUUID().toString()

        `when`("email is already registered") {
            every { userRepository.findByEmail(any()) } returns user

            then("it should throw IllegalArgumentException") {
                shouldThrow<IllegalArgumentException> {
                    authService.signup(request)
                }
            }
        }

        `when`("email is new") {
            every { userRepository.findByEmail(any()) } returns null
            every { passwordEncoder.encode(any()) } returns "encodedPw"
            every { userRepository.save(any()) } answers { spyk(firstArg<UserEntity>()){
                every { id } returns userId
            } }


            then("it should create a new user") {
                val response = authService.signup(request)
                response.email shouldBe request.email
                response.nickname shouldBe request.nickname
                
                verify(exactly = 1) { userRepository.save(any()) }
            }
        }
    }

    given("A login request") {
        val request = LoginRequest("test@example.com", "password")
        val session = MockHttpSession()

        `when`("credentials are valid") {
            val user = UserEntity("test@example.com", "encodedPw", "testuser")
            user.id // Mocked entity usually has null ID if not persisted or spyk, but AuthService.login uses ID in response
            // We need to ensure UserResponse.from(user) works. UserResponse needs ID!!.
            // Implementation of UserResponse.from calls user.id!! 
            // So we need to mock/stub ID reflecting persistence.
            // Since User is a class, we can just spy it or set it via reflection if needed, 
            // OR change User entity to open/data class to allow mocking comfortably.
            // But User is a JPA entity. 
            // Let's use reflection to set ID or assume the logic doesn't crash in test environment (Kotlin properties).
            // Actually, `User` class defined `val id: Long? = null`.
            // So user.id is null. user.id!! will throw NPE.
            // We should modify User Entity to allow setting ID for testing or use a constructor that allows it.
            // Or we can mock the `UserResponse.from` or just fix the test setup.
            // Let's use reflection to set the ID field for the test instance.
            val field = UserEntity::class.java.getDeclaredField("id")
            field.isAccessible = true
            field.set(user, 1L)

            every { userRepository.findByEmail(request.email) } returns user
            every { passwordEncoder.matches(request.password, user.password) } returns true

            then("it should return user response and create session") {
                val response = authService.login(request, session)
                response.email shouldBe user.email
                response.id shouldBe 1L
            }
        }
    }
})
