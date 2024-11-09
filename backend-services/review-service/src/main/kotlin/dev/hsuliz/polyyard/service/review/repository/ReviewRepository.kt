package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository :
    CoroutineCrudRepository<ReviewEntity, Long>, CoroutineSortingRepository<ReviewEntity, Long> {
  fun findAllBy(pageable: Pageable): Flow<ReviewEntity>
}
