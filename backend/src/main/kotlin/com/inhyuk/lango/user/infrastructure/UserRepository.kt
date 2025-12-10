package com.inhyuk.lango.user.infrastructure

import com.inhyuk.lango.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findByEmail(email: String): User?
}
