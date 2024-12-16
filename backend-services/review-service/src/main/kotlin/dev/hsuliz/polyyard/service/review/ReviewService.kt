package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.exception.ReviewAlreadyExistsException
import dev.hsuliz.polyyard.service.review.exception.ReviewResourceNotFoundException
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.repository.ResourceRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewCrudRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
import dev.hsuliz.polyyard.service.review.util.getCurrentUsername
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

    try {
      val reviews =
          reviewRepository.findReviewsBy(
              getCurrentUsername(), reviewResource.type, reviewResource.value)
      println("========")
      if (reviews.count() == 1) {
        throw ReviewAlreadyExistsException()
      }
      val savedResource =
          resourceRepository.findByTypeAndValue(reviewResource.type, reviewResource.value)!!
      val review = Review(reviewType, savedResource.id!!, rating, comment, savedResource)
      val savedReview = reviewCrudRepository.save(review)
      return savedReview
    } catch (reviewResourceNotFoundException: ReviewResourceNotFoundException) {
      val savedResource = resourceRepository.save(reviewResource)
      val review = Review(reviewType, savedResource.id!!, rating, comment, savedResource)
      val savedReview = reviewCrudRepository.save(review)
      return savedReview
    }
  }

  suspend fun deleteReview(reviewId: Long) {
    reviewCrudRepository.deleteById(reviewId)
  }
}
