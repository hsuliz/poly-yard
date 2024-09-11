package dev.hsuliz.bookservice.review

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewController(private val service: ReviewService) {

    @GetMapping("/users/{userId}/reviews")
    suspend fun getReviews(@PathVariable userId: Long): Flow<ReviewResponse> {
        val res = service.findUserReviews(userId)
        return res.map { ReviewResponse(it.reviewText, it.userId, it.bookId) }
    }
}
