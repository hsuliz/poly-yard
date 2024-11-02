package dev.hsuliz.polyyard.service.review.controller

import dev.hsuliz.polyyard.service.review.controller.dto.ReviewResponse
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.model.ReviewType
import dev.hsuliz.polyyard.service.review.service.ReviewService
import dev.hsuliz.polyyard.service.review.service.TypeService
import kotlinx.coroutines.flow.*
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

const val PREFERRED_USERNAME = "preferred_username"

@RestController
@RequestMapping("/api")
class ReviewController(
    private val reviewService: ReviewService,
    private val typeService: TypeService,
) {
  @GetMapping("/reviews")
  suspend fun getAllReviews(@PageableDefault pageable: Pageable): Flow<ReviewResponse> {
    val reviews = reviewService.findReviews(pageable)
    val types = typeService.findTypesBy(flow { reviews.map { it.id!! }.toList().forEach { emit(it) } })
    return reviews.zip(types) { review, type -> ReviewResponse(review, type) }
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
