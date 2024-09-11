package dev.hsuliz.bookservice.review

data class ReviewResponse(
    val reviewText: String,
    val userId: Long,
    val bookId: Long,
)
