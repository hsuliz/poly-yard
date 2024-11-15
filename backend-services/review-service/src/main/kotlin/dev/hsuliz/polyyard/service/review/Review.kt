package dev.hsuliz.polyyard.service.review

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class Review(
    @Column("type") val type: Type,
    val resourceId: Long,
    val rating: Int,
    val comment: String? = null,
    @CreatedBy val username: String? = null,
    @CreatedDate val createdAt: LocalDateTime? = null,
    @Id val id: Long? = null
) {
  @Transient lateinit var resource: Resource

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
