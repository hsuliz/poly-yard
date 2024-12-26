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
    val resourceIdCriteria =
        reviewResource?.let {
          val resourceQuery =
              query(Criteria.where("type").`is`(it.type).and("value").`is`(it.value))
          val resource =
              r2dbcEntityTemplate
                  .selectOne(resourceQuery, Review.Resource::class.java)
                  .awaitSingleOrNull() ?: return emptyFlow()

          Criteria.where("resource_id").`is`(resource.id!!)
        }

    val usernameCriteria = username?.let { Criteria.where("username").`is`(it) }

    val allCriteria = mutableListOf<Criteria>()
    resourceIdCriteria?.let { allCriteria.add(it) }
    usernameCriteria?.let { allCriteria.add(it) }

    val combinedCriteria =
        if (allCriteria.isNotEmpty()) {
          Criteria.from(allCriteria)
        } else {
          Criteria.empty()
        }

    val reviewQuery = query(combinedCriteria).with(pageable ?: Pageable.unpaged())
    return r2dbcEntityTemplate.select(reviewQuery, Review::class.java).asFlow()
  }
}
