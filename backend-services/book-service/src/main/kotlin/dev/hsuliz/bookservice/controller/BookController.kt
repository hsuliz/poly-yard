package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.service.BookService
import io.klogging.Klogging
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api/v1/{username}/book")
class BookController(private val bookService: BookService) : Klogging {

    //@GetMapping("/{id}")
    //suspend fun getBookById(
    //    @PathVariable username: String,
    //    @PathVariable id: String
    //): BookResponse {
    //    logger.debug("For user $username getting book by id $id")
    //    try {
    //        return bookService.getBookById(id).toResponse()
    //    } catch (exception: BookNotFoundException) {
    //        throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.message)
    //    } catch (exception: IllegalArgumentException) {
    //        throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, exception.message)
    //    }
    //}
//
    //@GetMapping
    //suspend fun findAllBooks(@PathVariable username: String): Flow<BookResponse> {
    //    logger.debug("Find all books for user $username")
    //    return bookService.findAllBooks().map { it.toResponse() }
    //}
//
    //@GetMapping("/title/{title}") // #TODO rework
    //fun findBooksByTitle(
    //    @PathVariable username: String,
    //    @PathVariable title: String
    //): Flow<BookResponse> {
    //    return bookService.findBooksByTitle(title).map { it.toResponse() }
    //}
}
