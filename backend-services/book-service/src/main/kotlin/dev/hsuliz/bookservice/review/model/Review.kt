package dev.hsuliz.bookservice.review.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("reviews")
data class Review(
    val userId: Long,
    val bookId: Long,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonIgnore
    @Id val id: Long? = null,
)
