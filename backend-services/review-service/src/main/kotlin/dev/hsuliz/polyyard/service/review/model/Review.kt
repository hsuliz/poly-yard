package dev.hsuliz.polyyard.service.review.model

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class Review(
    val username: String,
    val typeId: Long,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Id val id: Long? = null
)
