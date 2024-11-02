package dev.hsuliz.polyyard.service.review.dto

import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.model.ReviewType
import java.time.LocalDateTime

data class ReviewResponse(
    val id: Long,
    val username: String,
    val reviewType: ReviewType,
    val rating: Int,
    val comment: String?,
    val createdAt: LocalDateTime
)
