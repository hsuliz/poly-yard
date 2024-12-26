package dev.hsuliz.polyyard.service.review.model

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Table("reviews")
data class Review(
    @Column("type") val type: Type,
    val resourceId: Long,
    val rating: Int,
    val comment: String? = null,
    @Transient val resource: Resource? = null,
    @CreatedBy val username: String? = null,
    val createdAt: LocalDateTime? = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
    @Id val id: Long? = null
) {

  @PersistenceCreator
  private constructor(
      type: Type,
      resourceId: Long,
      rating: Int,
      comment: String? = null,
      username: String? = null,
      createdAt: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
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
