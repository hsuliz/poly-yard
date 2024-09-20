package dev.hsuliz.polyyard.gateway.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            authorizeExchange {
                authorize(pathMatchers(GET, "/token"), authenticated)

                authorize(pathMatchers(GET, "/users/**"), permitAll)
                authorize(pathMatchers(GET, "/reviews/**"), permitAll)
                authorize(pathMatchers(GET, "/books/**"), permitAll)

                authorize("/me/books/**", permitAll)
                authorize("/me/reviews/**", permitAll)
            }
            oauth2Login {}
            oauth2ResourceServer { jwt {} }
        }
    }
}
