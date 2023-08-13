package io.github.pedrovsn.identity.infrastructure.repository

import io.github.pedrovsn.identity.infrastructure.repository.orm.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<UserEntity>
}