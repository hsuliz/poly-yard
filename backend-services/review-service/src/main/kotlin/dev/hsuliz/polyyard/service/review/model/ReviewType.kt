package dev.hsuliz.polyyard.service.review.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("types")
data class ReviewType(val name: String, val externalId: Long, @Id val id: Long? = null)
