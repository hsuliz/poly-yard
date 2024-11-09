package dev.hsuliz.polyyard.service.review.entity

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class ReviewEntity(
    @Column("category") val category: Category,
    val resourceId: Long,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Id val id: Long? = null
) {
  enum class Category {
    BOOK,
    ALBUM
  }
}

@Table("resources")
data class ResourceEntity(val name: String, @Id val id: Long? = null) {
  enum class Resource {
    ISBN,
    ISRC,
    UPC
  }
}
