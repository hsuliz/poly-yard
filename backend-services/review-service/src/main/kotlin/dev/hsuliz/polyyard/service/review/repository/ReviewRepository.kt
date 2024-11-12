package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.Resource
import dev.hsuliz.polyyard.service.review.model.Review
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository :
    CoroutineCrudRepository<Review, Long>, CoroutineSortingRepository<Review, Long> {
  fun findAllBy(pageable: Pageable): Flow<Review>
}

interface ResourceRepository : CoroutineCrudRepository<Resource, Long>
