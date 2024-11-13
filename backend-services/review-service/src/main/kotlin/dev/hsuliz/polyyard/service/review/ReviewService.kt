package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.Flow
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val resourceRepository: ResourceRepository,
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
      reviewCategory: Review.Type,
      resource: Resource,
      rating: Int,
      comment: String? = null,
  ): Review {

    val savedResource = resourceRepository.save(resource)
    val savedReview =
        reviewRepository.save(Review(reviewCategory, savedResource.id!!, rating, comment))
    return savedReview
  }
}
