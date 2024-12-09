package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface ReviewCrudRepository :
    CoroutineCrudRepository<Review, Long>, CoroutineSortingRepository<Review, Long> {

  suspend fun countByUsername(username: String): Long

  fun findAllBy(pageable: Pageable): Flow<Review>

  fun findAllByUsername(username: String, pageable: Pageable): Flow<Review>

  fun findAllByResourceId(resourceId: Flow<Long>): Flow<Review>

}

interface ResourceRepository : CoroutineCrudRepository<Review.Resource, Long> {
  fun findByTypeAndValue(type: Review.Resource.Type, value: String): Flow<Review.Resource>
}
