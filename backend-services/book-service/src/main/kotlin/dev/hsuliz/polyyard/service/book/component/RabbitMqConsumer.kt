package dev.hsuliz.polyyard.service.book.component

import java.io.Serializable
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMqConsumer {

  @RabbitListener(queues = ["book"], ackMode = "MANUAL")
  private suspend fun receiveMessage(message: ReviewCreatedMessage) {

    println(message)
  }

  private data class ReviewCreatedMessage(val type: Type, val value: String) : Serializable {
    enum class Type {
      ISBN,
      ISRC,
      UPC
    }
  }
}
