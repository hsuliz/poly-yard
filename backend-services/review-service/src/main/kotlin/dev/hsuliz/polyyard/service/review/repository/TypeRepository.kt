package dev.hsuliz.polyyard.service.review.repository

import dev.hsuliz.polyyard.service.review.model.ReviewType
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository interface TypeRepository : CoroutineCrudRepository<ReviewType, Long>
