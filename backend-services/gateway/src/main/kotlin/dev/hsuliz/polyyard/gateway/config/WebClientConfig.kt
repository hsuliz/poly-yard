package dev.hsuliz.polyyard.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(@Value("\${book-service-uri}") private val bookServiceUri: String) {
  @Bean
  fun bookWebClient(): WebClient {
    return WebClient.builder().baseUrl(bookServiceUri).build()
  }
}
