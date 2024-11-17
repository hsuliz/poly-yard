package dev.hsuliz.polyyard.gateway

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

  @Bean
  fun bookWebClient(): WebClient {
    return WebClient.builder().baseUrl("http://localhost:8004").build()
  }
}
