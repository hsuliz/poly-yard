package dev.hsuliz.bookservice.review

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : CoroutineCrudRepository<Review, Long> {

    fun findReviewsByUserId(userId: Long): Flow<Review>
}
