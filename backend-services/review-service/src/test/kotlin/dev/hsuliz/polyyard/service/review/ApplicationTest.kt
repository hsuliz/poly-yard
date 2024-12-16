package dev.hsuliz.polyyard.service.review

import io.kotest.matchers.shouldBe

class ApplicationTest :
    IntegrationFunSpec({
      test("Context loaded") {}
      //test("Test container should be running") { postgresContainer.isRunning shouldBe true }
      test("Keycloak test container should be running") {
        keycloakContainer.isRunning shouldBe true
      }
    })
