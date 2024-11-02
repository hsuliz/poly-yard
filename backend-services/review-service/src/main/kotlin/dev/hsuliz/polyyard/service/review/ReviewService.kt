package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.model.ReviewType
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
import dev.hsuliz.polyyard.service.review.repository.TypeRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable.ofSize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val typeRepository: TypeRepository
) {

  fun findReviews(pageNumber: Int): Flow<Review> {
    val reviews = reviewRepository.findBy(ofSize(2).withPage(pageNumber))
    return reviews
  }

  @Transactional
  suspend fun createReview(
      username: String,
      reviewType: ReviewType,
      rating: Int,
      comment: String? = null,
  ): Review {
    val savedType = typeRepository.save(reviewType)

    val reviewToSave = Review(username, savedType.id!!, rating, comment)
    return reviewRepository.save(reviewToSave)
  }
}
