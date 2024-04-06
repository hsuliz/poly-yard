package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.dao.BookRequest
import dev.hsuliz.bookservice.dao.BookResponse
import dev.hsuliz.bookservice.service.BookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController(private val bookService: BookService) {
    @PostMapping
    suspend fun createBook(@RequestBody bookRequest: BookRequest): ResponseEntity<Unit> {
        bookService.saveBook(bookRequest.toModel())
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping
    fun findAllBooks(): Flow<BookResponse> {
        return bookService.findAllBooks().map { it.toResponse() }
    }
}
