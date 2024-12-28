package dev.hsuliz.polyyard.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers

const val SCOPE_USER = "SCOPE_USER"

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http {
      authorizeExchange {
        authorize(pathMatchers(GET, "/token"), authenticated)
        authorize(pathMatchers("/**"), permitAll)
        authorize(pathMatchers("/me/**"), hasAuthority(SCOPE_USER))
      }
      oauth2Login {}
      oauth2ResourceServer { jwt {} }
    }
  }
}
