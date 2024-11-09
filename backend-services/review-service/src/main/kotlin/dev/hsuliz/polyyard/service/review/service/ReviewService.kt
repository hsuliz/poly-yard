package dev.hsuliz.polyyard.service.review.service

import dev.hsuliz.polyyard.service.review.entity.Resource
import dev.hsuliz.polyyard.service.review.entity.Review
import dev.hsuliz.polyyard.service.review.repository.ResourceRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
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
      username: String,
      reviewCategory: Review.Category,
      resource: Pair<Resource.Type, String>,
      rating: Int,
      comment: String? = null,
  ): Review {
    val savedResource = resourceRepository.save(Resource(resource))
    val savedReview =
        reviewRepository.save(
            Review(username, reviewCategory, savedResource.id!!, rating, comment))
    return savedReview
  }
}
