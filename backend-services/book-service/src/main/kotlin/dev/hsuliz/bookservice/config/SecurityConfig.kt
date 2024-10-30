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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.WebFluxConfigurer

const val SCOPE_USER = "SCOPE_USER"

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig : WebFluxConfigurer {

  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http {
      authorizeExchange {
        authorize(pathMatchers(GET, "/**"), permitAll)
        authorize(pathMatchers("/me/**"), hasAuthority(SCOPE_USER))
      }
      oauth2ResourceServer { jwt {} }
    }
  }

  @Bean
  fun corsFilter(): CorsWebFilter {
    val config = CorsConfiguration().apply {
      allowedOrigins = listOf("http://localhost:5173")
      allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
      allowedHeaders = listOf("Authorization", "Content-Type")
      allowCredentials = true
    }

    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", config)

    return CorsWebFilter(source)
  }
}
