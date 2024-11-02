package dev.hsuliz.polyyard.service.review

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMqConsumer {

  @RabbitListener(queues = ["review"])
  suspend fun receiveMessage(message: String) {
    println(message)
  }
}
