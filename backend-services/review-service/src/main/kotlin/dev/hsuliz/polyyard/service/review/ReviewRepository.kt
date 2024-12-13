package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component

@Component
class ReviewRepository(private val template: R2dbcEntityTemplate) {

  suspend fun findReviewsBy(
      username: String?,
      resourceType: Review.Resource.Type?,
      resourceValue: String?,
      pageable: Pageable
  ): Flow<Review> {
    var resourceCriteria = Criteria.empty()
    var resource: Review.Resource? = null
    if (resourceType != null && resourceValue != null) {
      resourceCriteria =
          resourceCriteria.and("type").`is`(resourceType).and("value").`in`(resourceValue)
      resource =
          template.select(Query.query(resourceCriteria), Review.Resource::class.java).awaitFirst()
    }

    var reviewCriteria = Criteria.empty()
    resource?.let { reviewCriteria = reviewCriteria.and("resource_id").`is`(resource.id!!) }
    username?.let { reviewCriteria = reviewCriteria.and("username").`is`(it) }

    val reviewQuery = Query.query(reviewCriteria).with(pageable)
    val reviews = template.select(reviewQuery, Review::class.java).asFlow()

    return if (resource != null) {
      reviews.map { it.apply { this.resource = resource } }
    } else {
      val resources: Flow<Review.Resource> =
          template.select(Query.query(resourceCriteria), Review.Resource::class.java).asFlow()
      reviews.zip(resources) { review, itResource -> review.apply { this.resource = itResource } }
    }
  }
}
