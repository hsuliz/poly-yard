package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.exception.ReviewAlreadyExistsException
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.repository.ResourceRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewCrudRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
import dev.hsuliz.polyyard.service.review.util.currentUsername
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
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
      username: String? = null,
      resourceType: Review.Resource.Type? = null,
      resourceValue: String? = null,
      pageable: Pageable? = null
  ): Flow<Review> {
    val reviews = reviewRepository.findReviewsBy(username, resourceType, resourceValue, pageable)
    return reviews
  }

  @Transactional
  suspend fun createReview(
      reviewType: Review.Type,
      reviewResource: Review.Resource,
      rating: Int,
      comment: String? = null,
  ): Review {
    val review: Review
    val reviews =
        reviewRepository.findReviewsBy(currentUsername(), reviewResource.type, reviewResource.value)
    if (reviews.count() != 0) throw ReviewAlreadyExistsException()
    if (resourceRepository.existsByTypeAndValue(reviewResource.type, reviewResource.value)) {
      val existingResource =
          resourceRepository.getByTypeAndValue(reviewResource.type, reviewResource.value)!!
      review = Review(reviewType, existingResource.id!!, rating, comment, existingResource)
      return reviewCrudRepository.save(review)
    }
    val newResource =
        resourceRepository.save(Review.Resource(reviewResource.type, reviewResource.value))
    review = Review(reviewType, newResource.id!!, rating, comment, newResource)
    return reviewCrudRepository.save(review)
  }

  suspend fun deleteReview(reviewId: Long) {
    reviewCrudRepository.deleteById(reviewId)
  }
}
