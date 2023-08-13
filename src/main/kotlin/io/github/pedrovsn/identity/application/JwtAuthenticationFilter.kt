package io.github.pedrovsn.identity.application

import io.github.pedrovsn.identity.domain.AuthenticationService
import io.github.pedrovsn.identity.domain.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
        private val jwtTokenProvider: JwtTokenProvider,
        private val userService: UserDetailsService
): OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val tokenFromRequest = getTokenFromRequest(request)
        if(StringUtils.hasText(tokenFromRequest) && jwtTokenProvider.validateToken(tokenFromRequest)) {
            val username = jwtTokenProvider.getUsername(tokenFromRequest)
            val userDetails = userService.loadUserByUsername(username)
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
            )

            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource()
                    .buildDetails(request)

            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(httpServletRequest: HttpServletRequest): String {
        val bearerToken = httpServletRequest.getHeader("Authorization")
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length)
        }

        return ""
    }
}