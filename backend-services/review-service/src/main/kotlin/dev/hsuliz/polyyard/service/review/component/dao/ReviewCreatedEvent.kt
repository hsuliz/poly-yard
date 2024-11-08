package dev.hsuliz.polyyard.service.review.component.dao

import dev.hsuliz.polyyard.service.review.model.ReviewType

data class ReviewCreatedEvent(val reviewType: ReviewType, val rating: Int)
