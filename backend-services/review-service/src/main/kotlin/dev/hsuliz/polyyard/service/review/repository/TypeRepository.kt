package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.ReviewType
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TypeRepository : CoroutineCrudRepository<ReviewType, Long>
