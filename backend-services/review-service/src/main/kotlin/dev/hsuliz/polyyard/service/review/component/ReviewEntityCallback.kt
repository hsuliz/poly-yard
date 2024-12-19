package dev.hsuliz.polyyard.service.review.component

import dev.hsuliz.polyyard.service.review.model.Review
import org.reactivestreams.Publisher
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Lazy
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ReviewEntityCallback(
    @Lazy private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val rabbitTemplate: RabbitTemplate
) : AfterSaveCallback<Review>, AfterConvertCallback<Review> {

  override fun onAfterSave(
      entity: Review,
      outboundRow: OutboundRow,
      table: SqlIdentifier
  ): Publisher<Review> {
    rabbitTemplate.convertAndSend("book", ReviewCreatedMessage(entity.resource!!))
    return Mono.just(entity)
  }

  override fun onAfterConvert(entity: Review, table: SqlIdentifier): Publisher<Review> {
    return r2dbcEntityTemplate
        .select(
            Query.query(Criteria.where("id").`is`(entity.resourceId)), Review.Resource::class.java)
        .single()
        .map { entity.copy(resource = it) }
  }
}
