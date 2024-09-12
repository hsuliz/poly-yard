package dev.hsuliz.bookservice.review.dao

data class ReviewResponse(
    val reviewText: String,
    val userId: Long,
    val bookId: Long,
)
