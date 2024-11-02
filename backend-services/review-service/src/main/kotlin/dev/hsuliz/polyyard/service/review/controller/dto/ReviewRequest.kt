package dev.hsuliz.polyyard.service.review.controller.dto

data class ReviewRequest(
    val type: String,
    val rating: Int,
    val comment: String?,
)
