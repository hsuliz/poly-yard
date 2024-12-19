package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.Review
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

interface ReviewCrudRepository :
    CoroutineCrudRepository<Review, Long>, CoroutineSortingRepository<Review, Long> {

  suspend fun countByUsername(username: String): Long

  fun findAllBy(pageable: Pageable): Flow<Review>

  fun findAllByUsername(username: String, pageable: Pageable): Flow<Review>
}

interface ResourceRepository : CoroutineCrudRepository<Review.Resource, Long> {
  suspend fun getByTypeAndValue(type: Review.Resource.Type, value: String): Review.Resource?

  suspend fun existsByTypeAndValue(type: Review.Resource.Type, value: String): Boolean
}
