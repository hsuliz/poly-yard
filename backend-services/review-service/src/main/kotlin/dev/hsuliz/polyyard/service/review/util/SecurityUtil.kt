package dev.hsuliz.polyyard.service.review.util

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import reactor.core.publisher.Mono

suspend fun getCurrentUsername(): String = getCurrentUsernameMono().awaitSingle()

fun getCurrentUsernameMono(): Mono<String> =
    ReactiveSecurityContextHolder.getContext()
        .map { it.authentication }
        .filter { it.isAuthenticated }
        .map {
          val jwt = it.principal as Jwt
          return@map jwt.getClaimAsString("preferred_username")
        }
        .switchIfEmpty(mono { "anonymous" })
