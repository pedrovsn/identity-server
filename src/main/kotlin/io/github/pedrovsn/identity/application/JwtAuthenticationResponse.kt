package io.github.pedrovsn.identity.application

data class JwtAuthenticationResponse(
        val accessToken: String,
        val type: TokenType
)
