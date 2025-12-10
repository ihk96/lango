package com.inhyuk.lango.chat.infrastructure

import com.inhyuk.lango.chat.domain.ChatMessage
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findBySessionIdOrderByCreatedAtAsc(sessionId: Long): List<ChatMessage>
}
