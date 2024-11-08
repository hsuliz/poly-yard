package dev.hsuliz.polyyard.service.review.controller.dto

data class ReviewRequest(
    val type: ReviewTypeRequest,
    val rating: Int,
    val comment: String?,
)
