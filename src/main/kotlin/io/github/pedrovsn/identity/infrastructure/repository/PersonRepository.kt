package io.github.pedrovsn.identity.infrastructure.repository

import io.github.pedrovsn.identity.infrastructure.repository.orm.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<PersonEntity, Long> {
}