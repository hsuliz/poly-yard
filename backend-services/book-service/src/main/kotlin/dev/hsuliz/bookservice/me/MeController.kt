/*
package dev.hsuliz.bookservice.me

import dev.hsuliz.bookservice.me.dto.CreateBookRequest
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

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createBook(@RequestBody createBook: CreateBookRequest) {
        val contextHolder =
            ReactiveSecurityContextHolder.getContext().awaitFirst().authentication.principal as Jwt
        val username: String = contextHolder.claims["preferred_username"].toString()
        with(createBook) { meService.createBook(username, isbn, dateFinished, opinion) }
    }
}
*/
