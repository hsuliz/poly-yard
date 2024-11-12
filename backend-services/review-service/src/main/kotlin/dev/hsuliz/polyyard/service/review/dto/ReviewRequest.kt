package dev.hsuliz.polyyard.service.review.dto

import dev.hsuliz.polyyard.service.review.Review

data class ReviewRequest(
    val type: Review.Type,
    val resource: Resource,
    val rating: Int,
    val comment: String?
)

data class Resource(val id: String, val type: Review.Type)
