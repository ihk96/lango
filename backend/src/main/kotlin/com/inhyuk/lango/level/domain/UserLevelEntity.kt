package com.inhyuk.lango.level.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_levels")
class UserLevelEntity(
    val userId : String,
    val level : String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null

    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime = LocalDateTime.now()

}