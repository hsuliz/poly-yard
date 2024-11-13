package dev.hsuliz.polyyard.service.review.component

import dev.hsuliz.polyyard.service.review.Resource
import java.io.Serializable

data class ReviewCreatedMessage(val type: Resource.Type, val value: String) : Serializable {
  constructor(resource: Resource) : this(resource.type, resource.value)
}
