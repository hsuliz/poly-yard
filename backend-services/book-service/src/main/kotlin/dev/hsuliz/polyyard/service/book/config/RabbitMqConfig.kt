package dev.hsuliz.polyyard.service.book.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

  @Bean fun queue() = Queue("book", true)
}
