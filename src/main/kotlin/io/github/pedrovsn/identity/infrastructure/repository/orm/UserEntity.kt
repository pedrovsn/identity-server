package io.github.pedrovsn.identity.infrastructure.repository.orm

import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        val person: PersonEntity? = null,

        @Column(nullable = false, unique = true)
        val email: String = "",

        @Column(nullable = false)
        val password: String = "",

        val roles: List<String> = listOf()
)