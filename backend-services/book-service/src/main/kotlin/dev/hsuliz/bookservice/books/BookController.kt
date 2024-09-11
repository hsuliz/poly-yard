package dev.hsuliz.bookservice.books

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class BooksController(private val service: BookService) {

    @GetMapping("/books/{book_id}")
    suspend fun getBook(@PathVariable("book_id") bookId: Long): BookResponse? {
        val book = service.findBookById(bookId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND, "Book not found for id: $bookId"
        )
        return BookResponse(book.title)
    }
}
