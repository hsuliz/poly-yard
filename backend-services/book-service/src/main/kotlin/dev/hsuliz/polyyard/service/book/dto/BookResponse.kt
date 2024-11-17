package dev.hsuliz.polyyard.service.book.dto

import dev.hsuliz.polyyard.service.book.Book
import org.springframework.data.annotation.Id

data class BookResponse(
    val isbn: String,
    val title: String,
    val author: String,
    val publishedDate: Int,
    val pages: Int,
    val image: String,
    @Id val id: Long? = null
) {
  constructor(
      book: Book
  ) : this(book.isbn, book.title, book.author, book.publishedDate, book.pages, book.image, book.id)
}
