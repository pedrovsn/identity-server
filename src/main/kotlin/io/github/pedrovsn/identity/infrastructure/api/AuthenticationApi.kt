package io.github.pedrovsn.identity.infrastructure.api

import io.github.pedrovsn.identity.application.JwtAuthenticationResponse
import io.github.pedrovsn.identity.domain.AuthenticationService
import io.github.pedrovsn.identity.domain.Login
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationApi(
        private val authenticationService: AuthenticationService
) {

    @PostMapping
    fun authenticate(@RequestBody login: Login): ResponseEntity<JwtAuthenticationResponse> {
        val token = authenticationService.authenticate(login)
        return ResponseEntity.status(200).body(token)
    }


}