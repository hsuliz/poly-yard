package dev.hsuliz.bookservice.review

import dev.hsuliz.bookservice.book.BookService
import dev.hsuliz.bookservice.review.model.Review
import dev.hsuliz.bookservice.user.UserService
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val userService: UserService,
    private val reviewRepository: ReviewRepository,
    private val bookService: BookService,
) {

  @Transactional
  suspend fun createReview(
      username: String,
      bookIsbn: String,
      rating: Int,
      comment: String? = null,
  ): Review {
    val user = userService.findUser(username) ?: userService.createUser(username)
    val book = bookService.findExistingBook(bookIsbn) ?: bookService.createBook(bookIsbn)

    return reviewRepository.save(
        Review(
            user.id!!,
            book.id!!,
            rating,
            comment,
        )
    )
  }

  suspend fun deleteReview(username: String, reviewId: Long) {
    val user = userService.findUser(username) ?: error("User doesnt have reviews")
    val review = findReview(reviewId) ?: error("Review no found")
    when (review.userId) {
      user.id -> reviewRepository.delete(review)
      else -> error("No access")
    }
  }

  suspend fun findReview(reviewId: Long): Review? {
    return reviewRepository.findById(reviewId)
  }

  fun findUserReviews(userId: Long): Flow<Review> {
    return reviewRepository.findReviewsByUserId(userId)
  }
}
