package dev.hsuliz.bookservice.book

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class BookController(private val service: BookService) {

    @PostMapping("/me/books/{book_isbn}")
    suspend fun addBook(@PathVariable("book_isbn") bookIsbn: String): BookResponse? {
        val book =
            service.addBookByIsbn(bookIsbn)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found for isbn: $bookIsbn"
                )
        return BookResponse(book)
    }

    @GetMapping("/books/{book_isbn}")
    suspend fun getBook(@PathVariable("book_isbn") bookIsbn: String): BookResponse? {
        val book =
            service.findBookByIsbn(bookIsbn)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found for isbn: $bookIsbn"
                )
        return BookResponse(book)
    }
}
