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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createBook(@RequestBody bookRequest: BookRequest) =
        bookService.saveBook(bookRequest.toModel())

    @GetMapping
    fun getAllBooks(): Flow<BookResponse> = bookService.getAllBooks().map { it.toResponse() }
}
