package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val resourceRepository: ResourceRepository
) {

  fun findReviews(pageable: Pageable): Flow<Review> {
    return findReviewsWithResources { reviewRepository.findAllBy(pageable) }
  }

  fun findReviewsByUsername(username: String, pageable: Pageable): Flow<Review> {
    return findReviewsWithResources { reviewRepository.findAllByUsername(username, pageable) }
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
    val x = Review(reviewCategory, savedResource.id!!, rating, comment, savedResource)
    val savedReview = reviewRepository.save(x)
    return savedReview
  }

  private fun findReviewsWithResources(reviewsProvider: () -> Flow<Review>): Flow<Review> {
    val reviews = reviewsProvider()
    val resources = resourceRepository.findAllById(reviews.map { it.resourceId })
    return reviews.zip(resources) { review, resource -> review.apply { this.resource = resource } }
  }
}
