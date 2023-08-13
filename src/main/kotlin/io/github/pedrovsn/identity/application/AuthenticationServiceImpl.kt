package io.github.pedrovsn.identity.application

import io.github.pedrovsn.identity.domain.AuthenticationService
import io.github.pedrovsn.identity.domain.Login
import io.github.pedrovsn.identity.domain.UserNotFoundException
import io.github.pedrovsn.identity.domain.UserService
import io.github.pedrovsn.identity.infrastructure.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class AuthenticationServiceImpl(
        private val authenticationConfiguration: AuthenticationManager,
        private val jwtTokenProvider: JwtTokenProvider
): AuthenticationService {
    override fun authenticate(login: Login): JwtAuthenticationResponse {
        val authentication = authenticationConfiguration.authenticate(UsernamePasswordAuthenticationToken(
                login.username,
                login.password
        ))

        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenProvider.generateToken(authentication)

        return JwtAuthenticationResponse(token, TokenType.BEARER)
    }
}