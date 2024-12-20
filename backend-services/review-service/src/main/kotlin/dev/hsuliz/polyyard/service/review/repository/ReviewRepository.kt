package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component

@Component
class ReviewRepository(private val r2dbcEntityTemplate: R2dbcEntityTemplate) {

  suspend fun findReviewsBy(
      username: String? = null,
      reviewResource: Review.Resource? = null,
      pageable: Pageable? = null
  ): Flow<Review> {
    var resource: Review.Resource? = null

    if (reviewResource != null) {
      val resourceCriteria =
          Criteria.where("type").`is`(reviewResource.type).and("value").`in`(reviewResource.value)
      resource =
          r2dbcEntityTemplate
              .select(Query.query(resourceCriteria), Review.Resource::class.java)
              .asFlow()
              .singleOrNull()
    }

    var reviewCriteria = Criteria.empty()
    resource?.let { reviewCriteria = reviewCriteria.and("resource_id").`is`(resource.id!!) }
    username?.let { reviewCriteria = reviewCriteria.and("username").`is`(it) }

    val reviewQuery = Query.query(reviewCriteria).with(pageable ?: Pageable.unpaged())
    return r2dbcEntityTemplate.select(reviewQuery, Review::class.java).asFlow()
  }
}
