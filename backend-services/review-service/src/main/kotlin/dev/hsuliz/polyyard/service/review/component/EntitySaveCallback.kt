package dev.hsuliz.polyyard.service.review.component

import dev.hsuliz.polyyard.service.review.Review
import org.reactivestreams.Publisher
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class EntitySaveCallback(private val rabbitTemplate: RabbitTemplate) :
    AfterSaveCallback<Review> {

  override fun onAfterSave(
      entity: Review,
      outboundRow: OutboundRow,
      table: SqlIdentifier
  ): Publisher<Review> {
    rabbitTemplate.convertAndSend("book", ReviewCreatedMessage(entity.resource!!))
    println(entity)
    return Mono.just(entity)
  }
}
