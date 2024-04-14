package dev.hsuliz.bookservice.model

import dev.hsuliz.bookservice.dao.BookResponse
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Book(val title: String, val author: Author, val review: Review) {
    @Id var id = ObjectId()

    fun toResponse() = BookResponse(title, author, review)
}

data class Review(val rating: Int, val comment: String? = null) {
    init {
        require(rating in 0..5) { "Rating must be between 0 and 5" }
    }
}

data class Author(val firstName: String, val secondName: String? = null)
