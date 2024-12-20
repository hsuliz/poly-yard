package dev.hsuliz.polyyard.service.review.model

import java.time.LocalDateTime
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class Review(
    @Column("type") val type: Type,
    val resourceId: Long,
    val rating: Int,
    val comment: String? = null,
    @Transient var resource: Resource? = null,
    @CreatedBy val username: String? = null,
    @CreatedDate val createdAt: LocalDateTime? = null,
    @Id val id: Long? = null
) {

  @PersistenceCreator
  private constructor(
      type: Type,
      resourceId: Long,
      rating: Int,
      comment: String? = null,
      username: String? = null,
      createdAt: LocalDateTime? = null,
      id: Long? = null
  ) : this(type, resourceId, rating, comment, null, username, createdAt, id)

  enum class Type {
    BOOK,
    ALBUM
  }

  @Table("resources")
  data class Resource(val type: Type, val value: String, @Id val id: Long? = null) {
    enum class Type {
      ISBN,
      ISRC,
      UPC
    }
  }
}
