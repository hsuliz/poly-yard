package dev.hsuliz.bookservice.book.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Books")
data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val publishedDate: Int,
    val pages: Int,
    val image: String,
    @JsonIgnore @Id val id: Long? = null,
)
