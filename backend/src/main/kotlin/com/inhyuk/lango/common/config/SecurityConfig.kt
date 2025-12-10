package com.inhyuk.lango.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**", "/h2-console/**", "/error").permitAll()
                    .anyRequest().authenticated()
            }
            .headers {
                it.frameOptions { frameOptions -> frameOptions.disable() }
            }
            .formLogin { it.disable() }
            .logout {
                it.logoutUrl("/api/auth/logout")
                it.logoutSuccessHandler { _, response, _ ->
                    response.status = 200
                }
            }
        
        return http.build()
    }
}
