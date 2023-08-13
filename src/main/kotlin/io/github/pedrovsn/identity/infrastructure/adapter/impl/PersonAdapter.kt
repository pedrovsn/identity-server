package io.github.pedrovsn.identity.infrastructure.adapter.impl

import io.github.pedrovsn.identity.domain.Person
import io.github.pedrovsn.identity.infrastructure.adapter.DomainEntityAdapter
import io.github.pedrovsn.identity.infrastructure.repository.orm.PersonEntity
import org.springframework.stereotype.Component

@Component
class PersonAdapter: DomainEntityAdapter<Person, PersonEntity?> {
    override fun fromDomain(a: Person): PersonEntity {
        return PersonEntity(
            id = a.id, firstName = a.firstName, lastName = a.lastName, null, null
        )
    }

    override fun fromEntity(b: PersonEntity?): Person {
        if (b == null) {
            throw RuntimeException()
        }

        return Person(
                id = b.id,
                firstName = b.firstName,
                lastName = b.lastName
        )
    }
}