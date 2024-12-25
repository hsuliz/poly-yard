package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Component

@Component
class ReviewRepository(private val r2dbcEntityTemplate: R2dbcEntityTemplate) {

  suspend fun findReviewsBy(
      username: String? = null,
      reviewResource: Review.Resource? = null,
      pageable: Pageable? = null
  ): Flow<Review> {
    val resource: Review.Resource

    val resourceIdCriteria =
        if (reviewResource != null) {
          val resourceQuery =
              query(
                  Criteria.where("type")
                      .`is`(reviewResource.type)
                      .and("value")
                      .`in`(reviewResource.value))
          resource =
              r2dbcEntityTemplate
                  .selectOne(resourceQuery, Review.Resource::class.java)
                  .awaitSingleOrNull() ?: return emptyFlow()
          Criteria.where("resource_id").`is`(resource.id!!)
        } else {
          Criteria.empty()
        }

    val usernameCriteria = username?.let { Criteria.where("username").`is`(it) }

    val reviewQuery =
        query(Criteria.from(resourceIdCriteria, usernameCriteria))
            .with(pageable ?: Pageable.unpaged())

    val selectedReviews = r2dbcEntityTemplate.select(reviewQuery, Review::class.java).asFlow()
    return selectedReviews
  }
}
