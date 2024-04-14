package dev.hsuliz.bookservice.dao

import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Review

data class BookRequest(val title: String, val author: Author, val review: Review) {
    fun toModel(): Book = Book(title, author, review)
}
