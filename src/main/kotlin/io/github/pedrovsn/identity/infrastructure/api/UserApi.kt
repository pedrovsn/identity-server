package io.github.pedrovsn.identity.infrastructure.api

import io.github.pedrovsn.identity.domain.User
import io.github.pedrovsn.identity.domain.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/user")
class UserApi(
        private val userService: UserService,
        private val passwordEncoder: PasswordEncoder
) {

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        return ResponseEntity.status(201).body(userService.createUser(User(null, user.person, user.email, passwordEncoder.encode(user.password), user.roles)))
    }
}