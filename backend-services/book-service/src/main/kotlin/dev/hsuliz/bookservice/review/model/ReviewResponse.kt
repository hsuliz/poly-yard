package dev.hsuliz.bookservice.review.model

import dev.hsuliz.bookservice.book.model.Book
import dev.hsuliz.bookservice.user.model.User
import java.time.LocalDateTime

data class ReviewResponse(
    val user: User?,
    val book: Book?,
    val rating: Int,
    val comment: String,
    val createdAt: LocalDateTime,
) {
  constructor(
      review: Review
  ) : this(
      review.user,
      review.book,
      review.rating,
      review.comment ?: String(),
      review.createdAt,
  )
}
