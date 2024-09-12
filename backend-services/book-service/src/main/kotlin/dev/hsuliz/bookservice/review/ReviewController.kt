package dev.hsuliz.bookservice.review

import dev.hsuliz.bookservice.review.dao.ReviewResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ReviewController(private val service: ReviewService) {

    //@PostMapping("/reviews")
    //suspend fun addReview(@RequestBody reviewRequest: ReviewRequest): ReviewResponse? {
//
//
    //}

  @GetMapping("/reviews/{review_id}")
  suspend fun getReview(@PathVariable("review_id") reviewId: Long): ReviewResponse? {
      val res =
          service.findReview(reviewId)
              ?: throw ResponseStatusException(
                  HttpStatus.NOT_FOUND, "Review not found for id: $reviewId"
              )
    return ReviewResponse(res.reviewText, res.userId, res.bookId)
  }

  @GetMapping("/users/{user_id}/reviews")
  fun getUserReviews(@PathVariable("user_id") userId: Long): Flow<ReviewResponse> {
    val res = service.findUserReviews(userId)
    return res.map { ReviewResponse(it.reviewText, it.userId, it.bookId) }
  }
}
