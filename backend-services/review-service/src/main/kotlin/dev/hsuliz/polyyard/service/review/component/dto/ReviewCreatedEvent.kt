package dev.hsuliz.polyyard.service.review.component.dto


import dev.hsuliz.polyyard.service.review.model.Review

data class ReviewCreatedEvent(val reviewType: Review.Type, val rating: Int)
