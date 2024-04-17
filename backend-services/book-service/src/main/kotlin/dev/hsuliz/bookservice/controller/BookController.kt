package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.dao.BookRequest
import dev.hsuliz.bookservice.dao.BookResponse
import dev.hsuliz.bookservice.exception.BookAlreadyExistsException
import dev.hsuliz.bookservice.exception.BookNotFoundException
import dev.hsuliz.bookservice.service.BookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/book")
class BookController(private val bookService: BookService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createBook(@RequestBody bookRequest: BookRequest): BookResponse =
        try {
            bookService.saveBook(bookRequest.toModel()).toResponse()
        } catch (exception: BookAlreadyExistsException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, exception.message)
        }

    @GetMapping("/{id}")
    suspend fun getBookById(@PathVariable id: String): BookResponse =
        try {
            bookService.getBookById(id).toResponse()
        } catch (exception: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.message)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, exception.message)
        }

    @GetMapping
    fun findAllBooks(): Flow<BookResponse> = bookService.findAllBooks().map { it.toResponse() }

    @GetMapping("/title/{title}")
    fun findBooksByTitle(@PathVariable title: String): Flow<BookResponse> =
        bookService.findBooksByTitle(title).map { it.toResponse() }

    @DeleteMapping("/{id}")
    suspend fun deleteBookById(@PathVariable id: String) =
        try {
            bookService.deleteBookById(id)
        } catch (exception: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.message)
        }
}
