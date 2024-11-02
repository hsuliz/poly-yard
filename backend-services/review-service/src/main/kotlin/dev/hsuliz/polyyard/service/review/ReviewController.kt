package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.dto.ReviewResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.*

const val PREFERRED_USERNAME = "preferred_username"

@RestController
@RequestMapping("/api")
class ReviewController(
    private val service: ReviewService,
) {
  @GetMapping("/reviews")
  fun getAllReviews() {
    val reviews = 23

  }


  // @PostMapping("/me/reviews")
  // suspend fun addReview(
  //    @RequestBody reviewRequest: ReviewRequest,
  //    @AuthenticationPrincipal jwt: Jwt,
  // ) {
  //  val username = jwt.getClaimAsString(PREFERRED_USERNAME)
  //  println("$username is addung $reviewRequest")
  //  val review = with(reviewRequest) { service.createReview(username, Type(), rating, comment) }
  // }
  //
}
