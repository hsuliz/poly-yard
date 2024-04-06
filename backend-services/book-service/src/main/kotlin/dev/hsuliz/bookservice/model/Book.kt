package dev.hsuliz.bookservice.model

import dev.hsuliz.bookservice.dao.BookResponse
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Book(
    @Id val id: ObjectId = ObjectId(),
    val title: String,
    val author: Author,
    val review: Review
) {
    fun toResponse(): BookResponse = BookResponse(title, author, review)
}

data class Review(val rating: Int, val comment: String)

data class Author(val firstName: String, val secondName: String)
