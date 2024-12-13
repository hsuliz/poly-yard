package dev.hsuliz.polyyard.service.review.dto

import dev.hsuliz.polyyard.service.review.Review
import dev.hsuliz.polyyard.service.review.Review.Resource
import dev.hsuliz.polyyard.service.review.Review.Type
import java.time.LocalDateTime

data class ReviewResponse(
    val id: Long,
    val username: String,
    val type: Type,
    val resource: Resource,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime
) {
  constructor(
      review: Review
  ) : this(
      review.id!!,
      review.username!!,
      review.type,
      review.resource!!,
      review.rating,
      review.comment,
      review.createdAt!!)
}
