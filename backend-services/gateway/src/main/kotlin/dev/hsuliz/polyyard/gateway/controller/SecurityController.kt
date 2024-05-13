package dev.hsuliz.polyyard.gateway.controller

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class SecurityController {

    @GetMapping("/token")
    fun getHome(
        @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient
    ): Mono<String> {
        println(authorizedClient.accessToken.tokenValue)
        return Mono.just(authorizedClient.accessToken.tokenValue)
    }
}
