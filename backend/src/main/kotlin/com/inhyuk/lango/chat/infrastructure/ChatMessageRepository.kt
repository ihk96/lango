package com.inhyuk.lango.chat.infrastructure

import com.inhyuk.lango.chat.domain.ChatMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessageEntity, Long> {
    fun findBySessionIdOrderByCreatedAtAsc(sessionId: String): List<ChatMessageEntity>
}
