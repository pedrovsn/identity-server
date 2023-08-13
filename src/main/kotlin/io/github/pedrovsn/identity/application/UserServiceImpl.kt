package io.github.pedrovsn.identity.application

import io.github.pedrovsn.identity.domain.User
import io.github.pedrovsn.identity.domain.UserNotFoundException
import io.github.pedrovsn.identity.domain.UserService
import io.github.pedrovsn.identity.infrastructure.adapter.impl.UserAdapter
import io.github.pedrovsn.identity.infrastructure.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class UserServiceImpl(
        private val userAdapter: UserAdapter,
        private val userRepository: UserRepository
): UserService {
    override fun createUser(user: User): User {
        val userEntity = userAdapter.fromDomain(user)
        val save = userRepository.save(userEntity)
        return userAdapter.fromEntity(save)
    }

    override fun loadByEmail(username: String): User {
        return userRepository.findByEmail(username)
                .map { userAdapter.fromEntity(it) }
                .orElseThrow { UserNotFoundException("User $username not found") }
    }
}