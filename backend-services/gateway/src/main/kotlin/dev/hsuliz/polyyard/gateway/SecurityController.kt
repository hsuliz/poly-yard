package dev.hsuliz.polyyard.gateway

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController {

  @GetMapping("/token")
  fun getToken(@RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient): String =
      authorizedClient.accessToken.tokenValue
}
