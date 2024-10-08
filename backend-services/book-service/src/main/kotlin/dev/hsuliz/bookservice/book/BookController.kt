package dev.hsuliz.bookservice.book

import dev.hsuliz.bookservice.book.model.BookResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class BookController(private val service: BookService) {

  @GetMapping("/books/{book_isbn}")
  suspend fun getBook(@PathVariable("book_isbn") isbn: String): BookResponse {
    val book =
        service.findExistingBook(isbn)
            ?: try {
              val book = service.findAvailableBookToCreate(isbn)
              return BookResponse(book)
            } catch (e: IllegalArgumentException) {
              throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found for isbn: $isbn")
            }

    return BookResponse(book)
  }
}
