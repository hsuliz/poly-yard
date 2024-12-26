package dev.hsuliz.polyyard.service.review.dto

import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.model.Review.Resource

data class ReviewRequest(
    val type: Review.Type,
    val resource: ResourceRequest,
    val rating: Int,
    val comment: String?
) {
  data class ResourceRequest(val type: Resource.Type, val value: String) {
    fun toModel() = Resource(type, value)
  }
}
