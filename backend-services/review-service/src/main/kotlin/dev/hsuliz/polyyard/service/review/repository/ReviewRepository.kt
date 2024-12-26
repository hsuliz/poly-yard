package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
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
    val reviewCriteria = reviewCriteria(username, reviewResource) ?: return emptyFlow()
    val reviewQuery = query(reviewCriteria).with(pageable ?: Pageable.unpaged())
    return r2dbcEntityTemplate.select(reviewQuery, Review::class.java).asFlow()
  }

  suspend fun countReviewsBy(
      username: String? = null,
      reviewResource: Review.Resource? = null,
  ): Long {
    val reviewCriteria = requireNotNull(reviewCriteria(username, reviewResource))
    val reviewQuery = query(reviewCriteria)
    return r2dbcEntityTemplate.count(reviewQuery, Review::class.java).awaitSingle()
  }

  private suspend fun reviewCriteria(
      username: String? = null,
      reviewResource: Review.Resource? = null
  ): Criteria? {
    val resourceIdCriteria =
        reviewResource?.let {
          val resourceQuery =
              query(Criteria.where("type").`is`(it.type).and("value").`is`(it.value))
          val resource =
              r2dbcEntityTemplate
                  .selectOne(resourceQuery, Review.Resource::class.java)
                  .awaitSingleOrNull() ?: return null

          Criteria.where("resource_id").`is`(resource.id!!)
        }
    val usernameCriteria = username?.let { Criteria.where("username").`is`(it) }

    val criteria = mutableListOf<Criteria>()
    resourceIdCriteria?.let { criteria.add(it) }
    usernameCriteria?.let { criteria.add(it) }

    return if (criteria.isNotEmpty()) Criteria.from(criteria) else Criteria.empty()
  }
}
