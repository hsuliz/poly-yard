package dev.hsuliz.bookservice.review

import dev.hsuliz.bookservice.review.dao.ReviewRequest
import dev.hsuliz.bookservice.review.dao.ReviewResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class ReviewController(
    private val service: ReviewService,
) {

  @PostMapping("/me/reviews")
  suspend fun addReview(@RequestBody reviewRequest: ReviewRequest): ReviewResponse {
    val review = with(reviewRequest) { service.createReview("sasha", bookIsbn, rating, comment) }
    return ReviewResponse(review)
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
