package dev.hsuliz.polyyard.service.review

import dasniko.testcontainers.keycloak.KeycloakContainer
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
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
  }
}
