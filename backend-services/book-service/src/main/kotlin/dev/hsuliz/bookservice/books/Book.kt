package dev.hsuliz.bookservice.books

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Books")
data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val publishedDate: String,
    val numberOfPages: String,
    val image: String,
    @Id var id: Long? = null,
) {}
