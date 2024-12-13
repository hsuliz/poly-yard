package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.exception.ReviewAlreadyExists
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.repository.ResourceRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewCrudRepository
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
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
      reviewType: Review.Type,
      reviewResource: Review.Resource,
      rating: Int,
      comment: String? = null,
  ): Review {
    val currentUsername = getCurrentUsername()
    reviewRepository
        .findReviewsBy(currentUsername, reviewResource.type, reviewResource.value)
        .firstOrNull() ?: throw ReviewAlreadyExists("Review already exists")

    val savedResource = resourceRepository.save(reviewResource)
    val review = Review(reviewType, savedResource.id!!, rating, comment, savedResource)
    val savedReview = reviewCrudRepository.save(review)
    return savedReview
  }

  suspend fun deleteReview(reviewId: Long) {
    reviewCrudRepository.deleteById(reviewId)
  }

  private suspend fun getCurrentUsername(): String =
      ReactiveSecurityContextHolder.getContext()
          .map { it.authentication }
          .filter { it.isAuthenticated }
          .map {
            val jwt = it.principal as Jwt
            return@map jwt.getClaimAsString("preferred_username")
          }
          .switchIfEmpty(mono { "anonymous" })
          .awaitSingle()
}
