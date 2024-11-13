package dev.hsuliz.polyyard.service.review

import org.springframework.data.annotation.CreatedBy
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class Review(
    @Column("type") val type: Type,
    val resourceId: Long,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @CreatedBy
    val username: String? = null,
    @Id val id: Long? = null
) {
  enum class Type {
    BOOK,
    ALBUM
  }
}

@Table("resources")
data class Resource(val type: Type, val value: String, @Id val id: Long? = null) {
  enum class Type {
    ISBN,
    ISRC,
    UPC
  }
}
