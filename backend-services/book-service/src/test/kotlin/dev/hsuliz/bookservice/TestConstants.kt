package dev.hsuliz.bookservice

import dev.hsuliz.bookservice.dao.BookRequest
import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Review

object TestConstants {
    // Normal book
    private const val NORMAL_BOOK_TITLE = "Wolf"
    private val NORMAL_BOOK_AUTHOR = Author("Hesse", "Herman")
    private val NORMAL_BOOK_REVIEW = Review(5, "Good")
    val NORMAL_BOOK = Book(NORMAL_BOOK_TITLE, NORMAL_BOOK_AUTHOR, NORMAL_BOOK_REVIEW)
    val NORMAL_BOOK_REQUEST = BookRequest(NORMAL_BOOK_TITLE, NORMAL_BOOK_AUTHOR, NORMAL_BOOK_REVIEW)

    // NON_EXISTING_BOOK

    val NON_EXISTING_BOOK = Book("Shrek", Author("Hesse"), Review(5, "Good"))
}
