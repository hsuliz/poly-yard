package dev.hsuliz.bookservice.review.dao

data class ReviewRequest(
    val bookIsbn: String,
    val rating: Int,
    val comment: String?,
)
