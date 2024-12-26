package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.Review
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ReviewCrudRepository : CoroutineCrudRepository<Review, Long> {
  suspend fun existsByIdAndUsername(id: Long, username: String): Boolean
}

interface ResourceRepository : CoroutineCrudRepository<Review.Resource, Long> {
  suspend fun getByTypeAndValue(type: Review.Resource.Type, value: String): Review.Resource

  suspend fun existsByTypeAndValue(type: Review.Resource.Type, value: String): Boolean
}
