package dev.hsuliz.polyyard.service.review

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
abstract class IntegrationFunSpec(body: FunSpec.() -> Unit = {}) : FunSpec(body) {
  companion object {

    @Container
    @JvmField
    val keycloakContainer: KeycloakContainer =
        KeycloakContainer("quay.io/keycloak/keycloak:26.0")
            .withRealmImportFiles("polyyard-realm-keycloak-export.json")

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
