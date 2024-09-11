package dev.hsuliz.bookservice.book

import kotlinx.datetime.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Books")
data class Book(
    @Id val id: Long? = null,
    val isbn: String,
    val title: String,
    val author: String,
    val publishedDate: LocalDate,
    val numberOfPages: Int,
    val image: String,
)
