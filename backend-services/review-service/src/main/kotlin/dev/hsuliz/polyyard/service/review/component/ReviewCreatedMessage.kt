package dev.hsuliz.polyyard.service.review.component


import dev.hsuliz.polyyard.service.review.Review
import java.io.Serializable

data class ReviewCreatedMessage(val type: Review.Resource.Type, val value: String) : Serializable {
  constructor(resource: Review.Resource) : this(resource.type, resource.value)
}
