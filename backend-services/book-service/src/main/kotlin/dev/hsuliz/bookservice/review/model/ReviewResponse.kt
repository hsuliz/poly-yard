package dev.hsuliz.bookservice.review.model

import java.time.LocalDateTime

data class ReviewResponse(
    val id: Long,
    val rating: Int,
    val comment: String,
    val createdAt: LocalDateTime,
) {
  constructor(
      review: Review
  ) : this(
      review.id!!,
      review.rating,
      review.comment ?: String(),
      review.createdAt,
  )
}
