package com.inhyuk.lango.chat.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat_sessions")
class ChatSessionEntity(
    val userId: String,

    val title : String,

    @Column(columnDefinition = "TEXT")
    val scenario: String,

    @Column(nullable = false)
    val userRole: String,

    @Column(nullable = false)
    val aiRole: String,

    @Column(nullable = false)
    val userLevel: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null
}
