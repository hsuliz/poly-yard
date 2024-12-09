package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.dto.ReviewRequest
import dev.hsuliz.polyyard.service.review.dto.ReviewResponse
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

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
    val reviews =
        reviewService.findReviews(username, resourceType, resourceValue, pageable).toList()
    val response = reviews.map { ReviewResponse(it) }
    return PageImpl(response, pageable, reviewService.countReviews())
  }

  // @GetMapping("/reviews/test")
  // suspend fun findReviewsByResource(
  //    @RequestParam(value = "resource-type", required = false) resourceType:
  // Review.Resource.Type?,
  //    @RequestParam(value = "resource-value", required = false) resourceValue: String?,
  //    @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC)
  //    pageable: Pageable
  // ): Page<ReviewResponse> {
  //  val reviews =
  //      reviewService.findReviewsTest(resourceType, resourceValue, pageable).toList()
  //  val response = reviews.map { ReviewResponse(it) }
  //  return PageImpl(response, pageable, 2)
  // }

  @GetMapping("/reviews/{username}")
  suspend fun findReviewsByUsername(
      @PathVariable username: String,
      @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC)
      pageable: Pageable
  ): Page<ReviewResponse> {
    val reviews = reviewService.findReviewsByUsername(username, pageable).toList()
    val response = reviews.map { ReviewResponse(it) }
    return PageImpl(response, pageable, reviewService.countReviewsByUsername(username))
  }

  @PostMapping("/me/reviews")
  suspend fun addReview(@RequestBody reviewRequest: ReviewRequest) {
    val review =
        with(reviewRequest) {
          reviewService.createReview(type, resource.toModel(), rating, comment)
        }
  }
}
