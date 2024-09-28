package dev.hsuliz.bookservice.review

import dev.hsuliz.bookservice.review.model.ReviewRequest
import dev.hsuliz.bookservice.review.model.ReviewResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

const val PREFERRED_USERNAME = "preferred_username"

@RestController
class ReviewController(
    private val service: ReviewService,
) {

  @PostMapping("/me/reviews")
  suspend fun addReview(
      @RequestBody reviewRequest: ReviewRequest,
      @AuthenticationPrincipal jwt: Jwt,
  ): ReviewResponse {
    val username = jwt.getClaimAsString(PREFERRED_USERNAME)
    val review = with(reviewRequest) { service.createReview(username, bookIsbn, rating, comment) }
    return ReviewResponse(review)
  }

    @DeleteMapping("/me/reviews/{review_id}")
    suspend fun deleteReview(
        @PathVariable("review_id") reviewId: Long,
        @AuthenticationPrincipal jwt: Jwt,
    ) {
        val username = jwt.getClaimAsString(PREFERRED_USERNAME)
        service.deleteReview(username, reviewId)
    }

  @GetMapping("/reviews/{review_id}")
  suspend fun getReview(@PathVariable("review_id") reviewId: Long): ReviewResponse? {
    val review =
        service.findReview(reviewId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Review not found for id: $reviewId")
    return ReviewResponse(review)
  }

  @GetMapping("/users/{user_id}/reviews")
  fun getUserReviews(@PathVariable("user_id") userId: Long): Flow<ReviewResponse> {
    val reviews = service.findUserReviews(userId)
    return reviews.map { ReviewResponse(it) }
  }
}
