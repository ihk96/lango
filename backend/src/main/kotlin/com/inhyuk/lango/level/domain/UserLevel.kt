package com.inhyuk.lango.level.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_levels")
class UserLevel(
    val userId : String,
) {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null
}