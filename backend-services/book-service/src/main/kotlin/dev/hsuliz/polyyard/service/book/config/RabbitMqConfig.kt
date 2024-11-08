package dev.hsuliz.polyyard.service.book.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.SimpleMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

  @Bean fun queue() = Queue("book", true)

  @Bean
  fun converter(): SimpleMessageConverter {
    val converter = SimpleMessageConverter()
    converter.setAllowedListPatterns(listOf("kotlin.Pair"))
    return converter
  }
}
