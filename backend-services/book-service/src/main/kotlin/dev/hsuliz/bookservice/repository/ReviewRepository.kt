package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Review
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ReviewRepository : CoroutineCrudRepository<Review, String>
