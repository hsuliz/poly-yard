package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.dto.ReviewRequest
import dev.hsuliz.polyyard.service.review.dto.ReviewResponse
import dev.hsuliz.polyyard.service.review.exception.ReviewAlreadyExistsException
import dev.hsuliz.polyyard.service.review.model.Review
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

const val PREFERRED_USERNAME = "preferred_username"

@RestController
@RequestMapping("/api")
class ReviewController(private val reviewService: ReviewService) {

  @GetMapping("/reviews")
  suspend fun findReviews(
      @RequestParam(value = "username", required = false) username: String?,
      @RequestParam(value = "resource-type", required = false) resourceType: Review.Resource.Type?,
      @RequestParam(value = "resource-value", required = false) resourceValue: String?,
      @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC)
      pageable: Pageable
  ): Page<ReviewResponse> {
    require(resourceType != null && resourceValue != null)
    val reviews =
        reviewService
            .findReviews(username, Review.Resource(resourceType, resourceValue), pageable)
            .toList()
    val response = reviews.map { ReviewResponse(it) }
    return PageImpl(response, pageable, reviews.count().toLong())
  }

  @PostMapping("/me/reviews")
  suspend fun addReview(@RequestBody reviewRequest: ReviewRequest) {
    try {
      with(reviewRequest) { reviewService.createReview(type, resource.toModel(), rating, comment) }
    } catch (exception: ReviewAlreadyExistsException) {
      throw ResponseStatusException(HttpStatus.CONFLICT, exception.message)
    }
  }

  @DeleteMapping("/me/reviews/{review-id}")
  suspend fun deleteReview(@PathVariable("review-id") reviewId: Long) {
    reviewService.deleteReview(reviewId)
  }
}
