package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.dao.BookRequest
import dev.hsuliz.bookservice.dao.BookResponse
import dev.hsuliz.bookservice.service.BookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController(private val bookService: BookService) {
    @GetMapping
    fun findAllBooks(): Flow<BookResponse> = bookService.findAllBooks().map { it.toResponse() }

    @GetMapping("/{id}")
    suspend fun getBookById(@PathVariable id: String): BookResponse =
        bookService.getBookById(id).toResponse()

    @GetMapping("/title/{title}")
    fun findBooksByTitle(@PathVariable title: String): Flow<BookResponse> =
        bookService.findBooksByTitle(title).map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createBook(@RequestBody bookRequest: BookRequest): BookResponse =
        bookService.saveBook(bookRequest.toModel()).toResponse()

    @DeleteMapping("/{id}")
    suspend fun deleteBookById(@PathVariable id: String) = bookService.deleteBookById(id)
}
