package dev.hsuliz.bookservice.review.model

import java.time.LocalDateTime

data class ReviewWithBookAndUserDto(
    val reviewId: Long,
    val reviewRating: Int,
    val reviewComment: String,
    val reviewCreatedAt: LocalDateTime,
    val userId: Long,
    val userName: String,
    val bookId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val bookPages: Int,
    val bookImage: String,
    val bookIsbn: String,
    val bookPublishedDate: Int
)
