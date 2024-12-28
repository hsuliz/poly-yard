package dev.hsuliz.polyyard.gateway.filter

import dev.hsuliz.polyyard.gateway.dto.Resource

data class ReviewRequest(
    val type: String,
    val resource: Resource,
    val rating: Int,
    val comment: String
)
