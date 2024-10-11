package dev.hsuliz.bookservice.review.model

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.hsuliz.bookservice.book.model.Book
import dev.hsuliz.bookservice.user.model.User
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table

@Table("reviews")
data class Review(
    val userId: Long,
    val bookId: Long,
    val rating: Int,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonIgnore @Id val id: Long? = null,
) {
  @Transient var book: Book? = null
  @Transient var user: User? = null
}
