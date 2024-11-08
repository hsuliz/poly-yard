package dev.hsuliz.polyyard.service.book.rabbitmq

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class Consumer {

  @RabbitListener(queues = ["book"], ackMode = "MANUAL")
  suspend fun receiveMessage(message: String) {
    println(message)
  }
}
