package com.inhyuk.lango.chat.infrastructure

import com.inhyuk.lango.chat.domain.ChatSession
import org.springframework.data.jpa.repository.JpaRepository

interface ChatSessionRepository : JpaRepository<ChatSession, Long>
