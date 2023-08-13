package io.github.pedrovsn.identity.domain

import io.github.pedrovsn.identity.application.JwtAuthenticationResponse

interface AuthenticationService {

    fun authenticate(login: Login): JwtAuthenticationResponse
}