package dev.hsuliz.polyyard.service.book.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig : WebFluxConfigurer {

  companion object {
    const val SCOPE_USER = "SCOPE_USER"
  }

  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http {
      authorizeExchange {
        authorize(pathMatchers(GET, "/**"), permitAll)
        authorize(pathMatchers("/api/me/**"), hasAuthority(SCOPE_USER))
      }
      oauth2ResourceServer { jwt {} }
    }
  }
}
