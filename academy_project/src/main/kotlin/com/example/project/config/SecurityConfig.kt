package com.example.project.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { }
            csrf { disable() }
            authorizeRequests {
                authorize( HttpMethod.GET, "/car/count-check-ups", permitAll)
                authorize( HttpMethod.GET, "/car-check-up", permitAll)
                authorize( HttpMethod.GET, "/car-check-up/paged", permitAll)
                authorize( HttpMethod.GET, "/car/details-{id}", permitAll)
                authorize( HttpMethod.GET, "/car-check-up/{id}", permitAll)
                authorize( HttpMethod.DELETE, "/car-check-up/delete/{id}", hasAnyAuthority("SCOPE_ADMIN"))
                authorize( HttpMethod.DELETE, "/car/delete/{id}",  hasAnyAuthority("SCOPE_ADMIN"))
                authorize( HttpMethod.GET, "/car", hasAuthority("SCOPE_ADMIN"))
                authorize( HttpMethod.GET, "/car/paged",  hasAnyAuthority("SCOPE_ADMIN"))
                authorize( HttpMethod.GET, "/car/{id}", hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN"))
                authorize( HttpMethod.POST, "/car/add-with-model-id", hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN"))
                authorize( HttpMethod.POST, "/car/add-with-model-data", hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN"))
                authorize( HttpMethod.POST, "/car-check-up/add", hasAnyAuthority("SCOPE_ADMIN"))
                authorize( HttpMethod.GET, "/car-check-up/car/{carId}", hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN"))
                authorize( HttpMethod.GET, "/car-check-up/car/paged-{carId}", hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN"))
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt {}
            }
        }
        return http.build()
    }

    @Bean
    fun jwtDecoder(
        @Value("\${spring.security.oauth2.resourceserver.jwt.jwt-set-uri}") jwtSetUri: String
    ): JwtDecoder? {
        return NimbusJwtDecoder.withJwkSetUri(jwtSetUri).jwsAlgorithm(SignatureAlgorithm.RS256).build()
    }
}