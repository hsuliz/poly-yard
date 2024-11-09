package dev.hsuliz.polyyard.service.review.component.dto


import dev.hsuliz.polyyard.service.review.entity.Review

data class ReviewCreatedEvent(val reviewType: Review.Category, val rating: Int)
