package dev.hsuliz.polyyard.service.review.service


import dev.hsuliz.polyyard.service.review.entity.ReviewEntity
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

  fun findReviewsBy(pageable: Pageable): Flow<ReviewEntity> {
    val reviews = reviewRepository.findAllBy(pageable)
    return reviews
  }

  suspend fun countReviews(): Long {
    return reviewRepository.count()
  }

  suspend fun createReview() {
    reviewRepository.save(ReviewEntity(ReviewEntity.Category.BOOK, 2L, 2))
  }

  // @Transactional
  // suspend fun createReview(
  //    username: String,
  //    reviewType: ReviewType,
  //    rating: Int,
  //    comment: String? = null,
  // ): ReviewEntity {
  //  val savedType = typeRepository.save(reviewType)
  //  val reviewEntityToSave = ReviewEntity(username, savedType.id!!, rating, comment)
  //  val savedReview =
  //      reviewRepository
  //          .save(reviewEntityToSave)
  //          .alsoPublishEvent(eventPublisher, ReviewCreatedEvent(savedType, rating))
  //  return savedReview
  // }
}
