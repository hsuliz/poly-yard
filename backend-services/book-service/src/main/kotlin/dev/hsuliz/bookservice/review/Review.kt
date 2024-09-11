package dev.hsuliz.bookservice.review

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("reviews")
data class Review(
    @Id val id: Long? = null,
    val reviewText: String,
    val userId: Long,
    val bookId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
