package dev.hsuliz.bookservice.review

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class ReviewService(private val repository: ReviewRepository) {

    suspend fun findUserReviews(userId: Long): Flow<Review> {
        return repository.findReviewsByUserId(userId)
    }
}
