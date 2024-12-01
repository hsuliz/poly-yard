package dev.hsuliz.polyyard.service.book

import dev.hsuliz.polyyard.service.book.dto.BookResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class BookController(private val service: BookService) {

  // internal
  @GetMapping("/books")
  fun findBooksByIsbn(@RequestParam("isbn") isbns: List<String>): Flow<BookResponse> {
    val books = service.findBooksByIsbn(isbns)
    return books.map { BookResponse(it) }
  }

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
