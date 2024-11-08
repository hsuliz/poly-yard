package dev.hsuliz.polyyard.service.review.component

import dev.hsuliz.polyyard.service.review.component.dao.ReviewCreatedEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ReviewEventListener(private val rabbitTemplate: RabbitTemplate) {

  @EventListener
  suspend fun handleCreateReviewEvent(event: ReviewCreatedEvent) {
    if (event.reviewType.name == "book") {
      rabbitTemplate.convertAndSend("book", Pair(event.reviewType.externalId, event.rating))
    }
  }
}
