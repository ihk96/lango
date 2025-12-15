package com.inhyuk.lango.chat.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat_messages")
class ChatMessageEntity(
    val sessionId : String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(nullable = true, columnDefinition = "TEXT")
    val subContent: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val sender: MessageSender,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

enum class MessageSender {
    USER, AI, SYSTEM
}
