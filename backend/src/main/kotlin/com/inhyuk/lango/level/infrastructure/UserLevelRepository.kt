package com.inhyuk.lango.level.infrastructure

import com.inhyuk.lango.level.domain.UserLevelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLevelRepository : JpaRepository<UserLevelEntity, String> {
    fun findByUserId(userId: String): UserLevelEntity?
}