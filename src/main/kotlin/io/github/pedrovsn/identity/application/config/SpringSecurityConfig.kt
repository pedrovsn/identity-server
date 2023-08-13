package io.github.pedrovsn.identity.application.config

import io.github.pedrovsn.identity.application.JwtAuthenticationFilter
import io.github.pedrovsn.identity.application.JwtTokenProvider
import io.github.pedrovsn.identity.infrastructure.security.JwtAuthenticationEntryPoint
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SpringSecurityConfig(
        private val jwtTokenProvider: JwtTokenProvider,
        private val userDetailsService: UserDetailsService,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationManagerConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationManagerConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf { csrf -> csrf.disable() }
                .exceptionHandling { JwtAuthenticationEntryPoint() }
                .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .authorizeHttpRequests { auth ->
                    run {
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                    }
                }

        val jwtAuthenticationFilter = JwtAuthenticationFilter(jwtTokenProvider, userDetailsService)
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }
}