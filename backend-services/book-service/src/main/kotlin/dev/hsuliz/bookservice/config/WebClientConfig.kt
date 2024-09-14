package dev.hsuliz.bookservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun bookInfoClient(): WebClient =
        WebClient.builder().baseUrl("https://www.googleapis.com/books/v1/volumes").build()
}
