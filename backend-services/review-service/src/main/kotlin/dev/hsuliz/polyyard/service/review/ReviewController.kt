package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.dto.ReviewRequest
import dev.hsuliz.polyyard.service.review.dto.ReviewResponse
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

const val PREFERRED_USERNAME = "preferred_username"

@RestController
@RequestMapping("/api")
class ReviewController(
    private val reviewService: ReviewService,
) {

  @GetMapping("/reviews")
  suspend fun findAllReviews(@PageableDefault pageable: Pageable): Page<ReviewResponse> {
    val reviews = reviewService.findReviewsBy(pageable).toList()
    val response = reviews.map { ReviewResponse(it) }
    return PageImpl(response, pageable, reviewService.countReviews())
  }

  @PostMapping("/me/reviews")
  suspend fun addReview(@RequestBody reviewRequest: ReviewRequest) {
    val review =
        with(reviewRequest) {
          reviewService.createReview(type, resource.toModel(), rating, comment)
        }
  }
}
