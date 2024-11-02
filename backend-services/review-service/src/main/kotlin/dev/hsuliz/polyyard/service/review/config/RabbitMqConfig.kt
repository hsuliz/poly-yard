package dev.hsuliz.polyyard.service.review.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

  @Bean fun queue() = Queue("review", true)
}
