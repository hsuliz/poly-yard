package dev.hsuliz.bookservice.controller

import dev.hsuliz.bookservice.dao.AddBookRequest
import dev.hsuliz.bookservice.service.MeService
import io.klogging.Klogging
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/v1/me/books")
class MeController(private val meService: MeService) : Klogging {

    @PreAuthorize("hasAuthority('SCOPE_user')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addBook(@RequestBody addBookRequest: AddBookRequest) {
        val contextHolder =
            ReactiveSecurityContextHolder.getContext().awaitFirst().authentication.principal as Jwt
        val username: String = contextHolder.claims["preferred_username"].toString()
        with(addBookRequest) { meService.addBook(username, isbn, dateFinished, opinion) }
    }

    // @DeleteMapping
    // suspend fun deleteBookById(@RequestParam(required = true) id: String) {
    //    val contextHolder =
    //        ReactiveSecurityContextHolder.getContext().awaitFirst().authentication.principal as
    // Jwt
    //    val username = contextHolder.claims["preferred_user"]
    //
    //    logger.debug("For user $username deleting book by id $id")
    //    try {
    //        return bookService.deleteBookById(id)
    //    } catch (exception: BookNotFoundException) {
    //        throw ResponseStatusException(HttpStatus.NOT_FOUND, exception.message)
    //    }
    // }
}
