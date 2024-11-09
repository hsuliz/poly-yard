package dev.hsuliz.polyyard.service.review.component.dto


import dev.hsuliz.polyyard.service.review.entity.ReviewEntity

data class ReviewCreatedEvent(val reviewType: ReviewEntity.Category, val rating: Int)
