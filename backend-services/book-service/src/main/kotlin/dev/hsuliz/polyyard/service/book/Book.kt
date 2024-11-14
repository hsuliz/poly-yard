package dev.hsuliz.polyyard.service.book

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("books")
data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val publishedDate: Int,
    val pages: Int,
    val image: String,
    @Id val id: Long? = null,
)
