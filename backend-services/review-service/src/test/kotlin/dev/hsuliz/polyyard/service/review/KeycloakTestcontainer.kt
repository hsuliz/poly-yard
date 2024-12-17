package dev.hsuliz.polyyard.service.review

import dasniko.testcontainers.keycloak.KeycloakContainer
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
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
        }

    @DynamicPropertySource
    @JvmStatic
    fun jwtValidationProperties(registry: DynamicPropertyRegistry) {
      registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri") {
        keycloakContainer.authServerUrl + "/realms/polyyard/protocol/openid-connect/certs"
      }
    }

    init {
      keycloakContainer.start()
    }
  }
}
