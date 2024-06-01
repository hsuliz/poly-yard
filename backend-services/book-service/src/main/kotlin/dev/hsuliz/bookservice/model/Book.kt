package dev.hsuliz.bookservice.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Books")
data class Book(val isbn: String, val title: String, val author: Author) {
    @Id var id = ObjectId()
}

data class Author(val firstname: String, val lastname: String? = null)
