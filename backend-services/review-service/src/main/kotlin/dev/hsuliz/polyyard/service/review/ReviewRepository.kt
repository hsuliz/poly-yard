package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
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
    var resources: Flow<Review.Resource> = flowOf()
    if (resourceType != null && resourceValue != null) {
      resourceType.let { resourceCriteria = resourceCriteria.and("type").`is`(it) }
      resourceValue.let { resourceCriteria = resourceCriteria.and("value").`is`(it) }
      resources =
          template.select(Query.query(resourceCriteria), Review.Resource::class.java).asFlow()
    }

    var reviewCriteria = Criteria.empty()
    username?.let { reviewCriteria = reviewCriteria.and("username").`is`(it) }
    val reviewQuery = Query.query(reviewCriteria).with(pageable)
    var reviews = template.select(reviewQuery, Review::class.java).asFlow()
    if (reviews.count() == 0) {
      return reviews
    }

    if (resources.count() == 0) {
      resourceCriteria = Criteria.where("id").`in`(reviews.map { it.resourceId }.toSet())
      resources =
          template.select(Query.query(resourceCriteria), Review.Resource::class.java).asFlow()
    }

    return reviews.zip(resources) { review, resource -> review.apply { this.resource = resource } }
  }
}
