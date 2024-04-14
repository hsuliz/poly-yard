package dev.hsuliz.bookservice

import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Review

object TestConstants {
    // Book
    val NORMAL_BOOK = Book("Wolf", Author("Hesse", "Herman"), Review(5, "Good"))
    val NON_EXISTING_BOOK = Book("Shrek", Author("Hesse"), Review(5, "Good"))
}
