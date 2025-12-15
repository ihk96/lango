package com.inhyuk.lango.chat.infrastructure

import com.inhyuk.lango.chat.domain.ChatSessionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatSessionRepository : JpaRepository<ChatSessionEntity, String> {
    fun findByUserId(userId: String): List<ChatSessionEntity>
    fun findByUserIdOrderByCreatedAtDesc(userId: String): List<ChatSessionEntity>
}
