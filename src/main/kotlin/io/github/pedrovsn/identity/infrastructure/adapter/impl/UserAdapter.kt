package io.github.pedrovsn.identity.infrastructure.adapter.impl

import io.github.pedrovsn.identity.domain.Role
import io.github.pedrovsn.identity.domain.User
import io.github.pedrovsn.identity.infrastructure.adapter.DomainEntityAdapter
import io.github.pedrovsn.identity.infrastructure.repository.orm.UserEntity
import org.springframework.stereotype.Component

@Component
class UserAdapter(
        private val personAdapter: PersonAdapter,
): DomainEntityAdapter<User, UserEntity> {



    override fun fromDomain(a: User): UserEntity {
        return UserEntity(
                id = a.id,
                person = personAdapter.fromDomain(a.person),
                email = a.email,
                password = a.password,
                roles = a.roles.map { role -> role.name }.toList()
        )
    }

    override fun fromEntity(b: UserEntity): User {
        return User(
                id = b.id,
                person = personAdapter.fromEntity(b.person),
                email = b.email,
                password = b.password,
                roles = b.roles.map { role -> Role.valueOf(role) }.toList()
        )
    }
}