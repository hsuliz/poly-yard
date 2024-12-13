package dev.hsuliz.polyyard.service.review.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig {

  @Bean fun queue() = Queue("book", true)

  @Bean
  fun jsonMessageConverter(): Jackson2JsonMessageConverter {
    return Jackson2JsonMessageConverter()
  }

  @Bean
  fun rabbitTemplate(
      connectionFactory: ConnectionFactory,
      jsonMessageConverter: Jackson2JsonMessageConverter
  ): RabbitTemplate {
    val rabbitTemplate = RabbitTemplate(connectionFactory)
    rabbitTemplate.messageConverter = jsonMessageConverter
    return rabbitTemplate
  }
}
