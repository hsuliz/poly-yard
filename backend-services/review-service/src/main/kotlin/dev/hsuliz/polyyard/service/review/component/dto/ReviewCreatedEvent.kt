package dev.hsuliz.polyyard.service.review.component.dto


import dev.hsuliz.polyyard.service.review.Review

data class ReviewCreatedEvent(val reviewType: Review.Type, val rating: Int)
