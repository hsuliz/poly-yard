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

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addBook(@RequestBody addBookRequest: AddBookRequest) {
        val contextHolder =
            ReactiveSecurityContextHolder.getContext().awaitFirst().authentication.principal as Jwt
        val username: String = contextHolder.claims["preferred_username"].toString()
        println(contextHolder)
        with(addBookRequest) { meService.addBook(username, isbn, dateFinished, opinion) }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun check() {
        val contextHolder =
            ReactiveSecurityContextHolder.getContext().awaitFirst().authentication.principal as Jwt
        val username: String = contextHolder.claims["preferred_username"].toString()
        contextHolder.headers.entries.forEach { println(it) }
        contextHolder.claims.entries.forEach { println(it) }
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

/*
* cationKt in 1.409 seconds (process running for 1.755)
19:42:56.684290 ERROR [  reactor-http-nio-2] : i.n.r.d.DnsServerAdd : Unable to load io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider, fallback to system defaults. This may result in incorrect DNS resolutions on MacOS. Check whether you have a dependency on 'io.netty:netty-resolver-dns-native-macos'. Use DEBUG level to see the full stack: java.lang.UnsatisfiedLinkError: failed to load the required native library
kid=170x757mExtRVRpLKFPeIytCcywtz6Xs-rwmTQFe_n0
typ=JWT
alg=RS256
sub=b2f056b6-beb1-40ee-a0c5-07de68059f5c
resource_access={account={roles=[manage-account, manage-account-links, view-profile]}}
email_verified=false
allowed-origins=[/*]
iss=http://localhost:8080/realms/polyyard
typ=Bearer
preferred_username=sasha
sid=cef92620-e990-4323-8748-9c3fbc32c1e4
aud=[account]
acr=0
realm_access={roles=[offline_access, default-roles-polyyard, uma_authorization]}
azp=auth-client
auth_time=1721151177
scope=openid email USER profile
exp=2024-07-16T17:44:16Z
iat=2024-07-16T17:39:16Z
jti=b546bcd5-8b7f-4fb8-b766-9a95eb1cdab5
email=sasha@g.com
*
* */