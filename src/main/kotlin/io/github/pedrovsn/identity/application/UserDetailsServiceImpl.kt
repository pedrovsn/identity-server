package io.github.pedrovsn.identity.application

import io.github.pedrovsn.identity.domain.UserNotFoundException
import io.github.pedrovsn.identity.infrastructure.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class UserDetailsServiceImpl(
        private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if(username == null) {
            throw RuntimeException()
        }

        val user = userRepository.findByEmail(username)
                .orElseThrow { UserNotFoundException("User $username not found") }

        val authorities = ArrayList<SimpleGrantedAuthority>()
        user.roles.forEach { role ->
            run {
                authorities.add(SimpleGrantedAuthority(role))
            }
        }

        return org.springframework.security.core.userdetails.User(
                username,
                user.password,
                authorities
        )
    }
}