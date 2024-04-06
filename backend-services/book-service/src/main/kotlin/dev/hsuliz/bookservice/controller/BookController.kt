package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.dao.BookRequest
import dev.hsuliz.bookservice.service.BookService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/book")
class BookController(private val bookService: BookService) {
    @PutMapping
    suspend fun test(@RequestBody bookRequest: BookRequest) {
        bookService.saveBook(bookRequest.toModel())
    }
}
