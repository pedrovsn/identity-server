package io.github.pedrovsn.identity.infrastructure.repository.orm

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "persons")
class PersonEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val firstName: String = "",
        val lastName: String = "",
        val createdAt: LocalDateTime? = null,
        val updatedAt: LocalDateTime? = null
)