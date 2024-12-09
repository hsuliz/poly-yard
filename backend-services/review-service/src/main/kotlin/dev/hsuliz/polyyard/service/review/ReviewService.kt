package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.*
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewCrudRepository: ReviewCrudRepository,
    private val reviewRepository: ReviewRepository,
    private val resourceRepository: ResourceRepository
) {

  suspend fun findReviews(
      username: String?,
      resourceType: Review.Resource.Type?,
      resourceValue: String?,
      pageable: Pageable
  ): Flow<Review> {
    val reviews = reviewRepository.findReviewsBy(username, resourceType, resourceValue, pageable)
    return reviews
  }

  fun findReviewsByUsername(username: String, pageable: Pageable): Flow<Review> {
    return findReviewsWithResources { reviewCrudRepository.findAllByUsername(username, pageable) }
  }

  suspend fun countReviewsByUsername(username: String): Long {
    return reviewCrudRepository.countByUsername(username)
  }

  suspend fun countReviews(): Long {
    return reviewCrudRepository.count()
  }

  @Transactional
  suspend fun createReview(
      reviewCategory: Review.Type,
      resource: Review.Resource,
      rating: Int,
      comment: String? = null,
  ): Review {

    val savedResource = resourceRepository.save(resource)
    val review = Review(reviewCategory, savedResource.id!!, rating, comment, savedResource)
    val savedReview = reviewCrudRepository.save(review)
    return savedReview
  }

  private fun findReviewsWithResources(reviewsProvider: () -> Flow<Review>): Flow<Review> {
    val reviews = reviewsProvider()
    val resources = resourceRepository.findAllById(reviews.map { it.resourceId })
    return reviews.zip(resources) { review, resource -> review.apply { this.resource = resource } }
  }
}
