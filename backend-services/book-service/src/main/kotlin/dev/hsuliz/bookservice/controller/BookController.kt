package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.dao.BookRequest
import dev.hsuliz.bookservice.dao.BookResponse
import dev.hsuliz.bookservice.exception.BookAlreadyExistsException
import dev.hsuliz.bookservice.exception.BookNotFoundException
import dev.hsuliz.bookservice.service.BookService
import io.klogging.Klogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@CrossOrigin
@RestController
@RequestMapping("/api/{username}/book")
class BookController(private val bookService: BookService) : Klogging {

    @PreAuthorize("authentication.principal.claims['preferred_username'] == #username")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createBook(
        @PathVariable username: String,
        @RequestBody bookRequest: BookRequest
    ): BookResponse {
        try {
            return bookService.saveBook(bookRequest.toModel()).toResponse()
        } catch (exception: BookAlreadyExistsException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, exception.message)
        }
    }

    @GetMapping("/{id}")
    suspend fun getBookById(
        @PathVariable username: String,
        @PathVariable id: String
    ): BookResponse {
        logger.debug("For user $username getting book by id $id")
        try {
            return bookService.getBookById(id).toResponse()
        } catch (exception: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.message)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, exception.message)
        }
    }

    @GetMapping
    suspend fun findAllBooks(@PathVariable username: String): Flow<BookResponse> {
        logger.debug("Find all books for user $username")
        return bookService.findAllBooks().map { it.toResponse() }
    }

    @GetMapping("/title/{title}") // #TODO rework
    fun findBooksByTitle(
        @PathVariable username: String,
        @PathVariable title: String
    ): Flow<BookResponse> {
        return bookService.findBooksByTitle(title).map { it.toResponse() }
    }

    @PreAuthorize("authentication.principal.claims['preferred_username'] == #username")
    @DeleteMapping("/{id}")
    suspend fun deleteBookById(@PathVariable username: String, @PathVariable id: String) {
        logger.debug("For user $username deleting book by id $id")
        try {
            return bookService.deleteBookById(id)
        } catch (exception: BookNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.message)
        }
    }
}
