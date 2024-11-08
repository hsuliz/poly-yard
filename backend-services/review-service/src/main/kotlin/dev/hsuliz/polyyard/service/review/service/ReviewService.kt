package dev.hsuliz.polyyard.service.review.service

import dev.hsuliz.polyyard.service.review.component.dao.ReviewCreatedEvent
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.model.ReviewType
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
import dev.hsuliz.polyyard.service.review.repository.TypeRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val typeRepository: TypeRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

  fun findReviewsBy(pageable: Pageable): Flow<Review> {
    val reviews = reviewRepository.findAllBy(pageable)
    return reviews
  }

  suspend fun countReviews(): Long {
    return reviewRepository.count()
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
    val savedReview = reviewRepository.save(reviewToSave)

    eventPublisher.publishEvent(ReviewCreatedEvent(savedType, savedReview.rating))
    return savedReview
  }
}
