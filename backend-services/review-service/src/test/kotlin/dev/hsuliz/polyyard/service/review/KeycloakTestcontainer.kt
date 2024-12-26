package dev.hsuliz.polyyard.service.review

import com.fasterxml.jackson.annotation.JsonProperty
import dasniko.testcontainers.keycloak.KeycloakContainer
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface KeycloakTestcontainer {
  companion object {
    @Container
    @JvmField
    val keycloakContainer =
        KeycloakContainer("quay.io/keycloak/keycloak:26.0").apply {
          withRealmImportFiles("polyyard-realm-keycloak-export.json")
          start()
        }

    @DynamicPropertySource
    @JvmStatic
    fun jwtValidationProperties(registry: DynamicPropertyRegistry) {
      registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri") {
        keycloakContainer.authServerUrl + "/realms/polyyard/protocol/openid-connect/certs"
      }
      registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri") {
        keycloakContainer.authServerUrl + "/realms/polyyard"
      }
    }

    @JvmField
    val keycloakAdminClient: Keycloak =
        KeycloakBuilder.builder()
            .serverUrl(keycloakContainer.authServerUrl)
            .realm("master")
            .clientId("admin-cli")
            .username(keycloakContainer.adminUsername)
            .password(keycloakContainer.adminPassword)
            .build()

    init {
      populateKeycloakUsers()
    }

    fun fetchToken(username: String): String {
      val body =
          BodyInserters.fromFormData("grant_type", "password")
              .with("client_id", "back-end-client")
              .with("client_secret", "**********")
              .with("username", username)
              .with("password", "password123")

      val token =
          WebClient.create()
              .post()
              .uri(
                  "${keycloakContainer.authServerUrl}/realms/polyyard/protocol/openid-connect/token")
              .body(body)
              .retrieve()
              .bodyToMono(KeyCloakToken::class.java)
              .block()!!

      return token.accessToken
    }

    data class KeyCloakToken(@JsonProperty("access_token") val accessToken: String)

    private fun populateKeycloakUsers() {
      val users =
          listOf(
              User("user1", "user1@example.com", "Test", "User", "password123"),
              User("user2", "user2@example.com", "Test", "User", "password123"),
              User("user3", "user3@example.com", "Test", "User", "password123"),
              User("user4", "user4@example.com", "Test", "User", "password123"))

      users.forEach { user -> createKeycloakUser(user) }
    }

    private fun createKeycloakUser(user: User) {
      val userRepresentation =
          UserRepresentation().apply {
            username = user.username
            isEnabled = true
            email = user.email
            firstName = user.firstName
            lastName = user.lastName
            credentials =
                listOf(
                    CredentialRepresentation().apply {
                      type = CredentialRepresentation.PASSWORD
                      value = user.password
                      isTemporary = false
                    })
          }

      keycloakAdminClient.realm("polyyard").users().create(userRepresentation)
    }

    data class User(
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String
    )
  }
}
