package dev.hsuliz.polyyard.service.review

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class Review(
    val username: String,
    @Column("type") val type: Type,
    val resourceId: Long,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
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
