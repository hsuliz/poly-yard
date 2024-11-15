package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
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
    val resources = resourceRepository.findAllById(reviews.map { it.resourceId })
    return reviews.zip(resources) { review, resource -> review.apply { this.resource = resource } }
  }

  suspend fun countReviews(): Long {
    return reviewRepository.count()
  }

  @Transactional
  suspend fun createReview(
      reviewCategory: Review.Type,
      resource: Review.Resource,
      rating: Int,
      comment: String? = null,
  ): Review {
    val savedResource = resourceRepository.save(resource)
    val x = Review(reviewCategory, savedResource.id!!, rating, comment)
    x.resource = savedResource
    val savedReview = reviewRepository.save(x)
    return savedReview
  }
}
