package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.dto.ReviewRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

const val PREFERRED_USERNAME = "preferred_username"

@RestController
@RequestMapping("/api")
class ReviewController(
    private val reviewService: ReviewService,
) {

  @PostMapping("/me/reviews")
  suspend fun addReview(
      @RequestBody reviewRequest: ReviewRequest,
      @AuthenticationPrincipal jwt: Jwt,
  ) {
    val username = jwt.getClaimAsString(PREFERRED_USERNAME)
    println("$username is addung $reviewRequest")
    val review =
        with(reviewRequest) {
          reviewService.createReview(
              type,
              resource.toModel(),
              rating,
              comment
          )
        }
  }
}
