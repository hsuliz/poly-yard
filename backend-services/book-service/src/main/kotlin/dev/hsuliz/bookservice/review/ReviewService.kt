package dev.hsuliz.bookservice.review

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class ReviewService(
  private val reviewRepository: ReviewRepository
) {

  //suspend fun saveReview(bookIsbn: String, rating: Int, comment: String?): Review? {
  //
  //}

  suspend fun findReview(reviewId: Long): Review? {
    return reviewRepository.findById(reviewId)
  }

  fun findUserReviews(userId: Long): Flow<Review> {
    return reviewRepository.findReviewsByUserId(userId)
  }
}
