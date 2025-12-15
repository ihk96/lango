package com.inhyuk.lango.user.application

import com.inhyuk.lango.user.domain.UserEntity
import com.inhyuk.lango.user.domain.UserRole
import com.inhyuk.lango.user.dto.LoginRequest
import com.inhyuk.lango.user.dto.SignupRequest
import com.inhyuk.lango.user.dto.UserResponse
import com.inhyuk.lango.user.infrastructure.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signup(request: SignupRequest): UserResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        val encodedPw = passwordEncoder.encode(request.password) ?: throw Exception("비밀번호 암호화 실패")
        val user = UserEntity(
            email = request.email,
            password = encodedPw,
            nickname = request.nickname,
            role = UserRole.USER
        )
        
        return UserResponse.from(userRepository.save(user))
    }
    
    @Transactional(readOnly = true)
    fun login(request: LoginRequest, session: HttpSession): UserResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Invalid email or password")
            
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }
        val id = requireNotNull(user.id) { throw IllegalArgumentException("User ID cannot be null") }
        val authentication = UsernamePasswordAuthenticationToken(
            id,
            null,
            listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        )
        val securityContext = SecurityContextHolder.createEmptyContext()
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
        
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext)
        
        return UserResponse.from(user)
    }
}
