package com.inhyuk.lango.user.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var nickname: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    var currentLevel: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null
}

enum class UserRole {
    USER, ADMIN
}
