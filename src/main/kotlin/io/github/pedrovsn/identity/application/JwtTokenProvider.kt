package io.github.pedrovsn.identity.application

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
        @Value("\${app.jwt.secret}")
        private val jwtSecret: String,
        @Value("\${app.jwt.expiration-milliseconds}")
        private val jwtExpirationDate: Long
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val authorities = authentication.authorities.map { grantedAuthority -> grantedAuthority.authority }.toSet()
        val expirationDate = Date(Date().time + jwtExpirationDate)
        val token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date())
                .setExpiration(expirationDate)
                .signWith(key())
                .addClaims(mapOf(Pair("roles", authorities)))
                .compact()

        return token
    }

    private fun key(): Key? {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        )
    }

    fun getUsername(token: String): String {
        val claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .body

        return claims.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token)

            return true
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", ex.message)
        } catch (ex: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", ex.message)
        } catch (ex: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", ex.message)
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", ex.message);
        }

        return false
    }

}