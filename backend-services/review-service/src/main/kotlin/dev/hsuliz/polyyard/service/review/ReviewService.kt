package dev.hsuliz.polyyard.service.review

import kotlinx.coroutines.flow.Flow
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

  suspend fun deleteReview(reviewId: Long) {
    reviewCrudRepository.deleteById(reviewId)
  }
}
