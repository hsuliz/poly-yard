package dev.hsuliz.bookservice.review.model

data class ReviewRequest(
    val bookIsbn: String,
    val rating: Int,
    val comment: String?,
)
