package com.inhyuk.lango.chat.domain

import com.inhyuk.lango.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat_sessions")
class ChatSession(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
