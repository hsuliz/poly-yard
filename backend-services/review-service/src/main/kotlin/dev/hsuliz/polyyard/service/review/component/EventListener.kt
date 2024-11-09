package dev.hsuliz.polyyard.service.review.component

import dev.hsuliz.polyyard.service.review.component.dto.ReviewCreatedEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ReviewEventListener(private val rabbitTemplate: RabbitTemplate) {

  @EventListener
  suspend fun reviewCreatedEvent(event: ReviewCreatedEvent) {
    when (event.reviewType.name) {
      "book" -> {
        withContext(Dispatchers.IO) {
          rabbitTemplate.convertAndSend("book", Pair(event.reviewType.name, event.rating))
        }
      }
    }
  }
}
