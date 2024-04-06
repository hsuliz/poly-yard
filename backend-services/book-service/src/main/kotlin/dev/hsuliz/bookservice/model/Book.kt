package dev.hsuliz.bookservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Book(
    @Id val id: String? = null,
    val title: String,
    val author: Author,
    val review: Review
)
