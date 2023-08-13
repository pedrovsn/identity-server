package io.github.pedrovsn.identity.domain

data class User(
        val id: Long?,
        val person: Person,
        val email: String,
        val password: String,
        val roles: List<Role>
)
