package dev.hsuliz.polyyard.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(@Value("\${book-service-host}") private val bookServiceHost: String) {
  @Bean
  fun bookWebClient(): WebClient {
    return WebClient.builder().baseUrl(bookServiceHost).build()
  }
}
