package dev.hsuliz.polyyard.service.book.component

import dev.hsuliz.polyyard.service.book.BookRepository
import dev.hsuliz.polyyard.service.book.BookService
import java.io.Serializable
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
private class RabbitMqConsumer(
    private val bookRepository: BookRepository,
    private val bookService: BookService
) {

  @RabbitListener(queues = ["book"], ackMode = "MANUAL")
  suspend fun receiveMessage(message: ReviewCreatedMessage) {
    if (message.type != ReviewCreatedMessage.Type.ISBN) return
    if (bookRepository.existsBy(message.value)) return

    // The infinite loop happens because the message keeps getting reprocessed after the exception
    // is thrown, possibly due to the message being redelivered by RabbitMQ since no acknowledgment
    // is sent.
    try {
      val newBook = bookService.findAvailableBookToCreate( message.value)
      bookRepository.save(newBook)
    } catch (e: Exception) {
      println("Book error")
    }
  }

  data class ReviewCreatedMessage(val type: Type, val value: String) : Serializable {
    enum class Type {
      ISBN
    }
  }
}
