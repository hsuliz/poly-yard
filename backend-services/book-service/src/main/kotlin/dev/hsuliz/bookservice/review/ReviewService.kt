package dev.hsuliz.bookservice.review

import dev.hsuliz.bookservice.book.BookService
import dev.hsuliz.bookservice.book.model.Book
import dev.hsuliz.bookservice.review.model.Review
import dev.hsuliz.bookservice.user.UserService
import dev.hsuliz.bookservice.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        ))
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
    val review = reviewRepository.findById(reviewId)
    return review
  }

  fun findAllReviews(): Flow<Review> {
    val x = reviewRepository.findAllReviewsWithBookAndUser()
    val review =
        x.map {
          val review =
              Review(
                  it.userId,
                  it.bookId,
                  it.reviewRating,
                  it.reviewComment,
                  it.reviewCreatedAt,
                  it.reviewId,
              )
          review.book =
              Book(
                  it.bookIsbn,
                  it.bookTitle,
                  it.bookAuthor,
                  it.bookPublishedDate,
                  it.bookPages,
                  it.bookImage,
                  it.bookId,
              )

          review.user = User(it.userName, it.userId)
          review
        }
    return review
  }

  fun findUserReviews(userId: Long): Flow<Review> {
    return reviewRepository.findReviewsByUserId(userId)
  }
}
