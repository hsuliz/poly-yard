package dev.hsuliz.polyyard.gateway.dto

data class ReviewRequest(
    val type: String,
    val resource: Resource,
    val rating: Int,
    val comment: String
)
