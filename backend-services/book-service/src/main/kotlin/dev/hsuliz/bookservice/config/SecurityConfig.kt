package dev.hsuliz.bookservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http {
      authorizeExchange {
        authorize(pathMatchers(GET, "/users/**"), permitAll)
        authorize(pathMatchers(GET, "/reviews/**"), permitAll)
        authorize(pathMatchers(GET, "/books/**"), permitAll)
        authorize("/me/books/**", permitAll)

        // authorize(pathMatchers(POST, "/me/books/**"), permitAll)
        // authorize(pathMatchers(GET, "/api/v1/me/books/**"), permitAll)
        // authorize(pathMatchers(POST, "/api/v1/me/books/**"), authenticated)
        // authorize(pathMatchers(DELETE, "/api/v1/me/books/**"), authenticated)
      }
      csrf { disable() }
      oauth2ResourceServer { jwt {} }
    }
  }
}
