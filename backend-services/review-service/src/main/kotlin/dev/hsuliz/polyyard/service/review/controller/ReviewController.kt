package dev.hsuliz.polyyard.service.review.controller

import dev.hsuliz.polyyard.service.review.controller.dto.ReviewRequest

import dev.hsuliz.polyyard.service.review.service.ReviewService
import kotlinx.coroutines.flow.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
   //val review =
   //    with(reviewRequest) {
   //      reviewService.createReview(
   //          username, ReviewType(type.name, type.externalId), rating, comment)
   //    }
  }
}
