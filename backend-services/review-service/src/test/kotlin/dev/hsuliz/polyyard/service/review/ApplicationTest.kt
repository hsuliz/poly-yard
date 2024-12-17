package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.KeycloakTestcontainer.Companion.keycloakContainer
import dev.hsuliz.polyyard.service.review.PostgresTestcontainer.Companion.postgresContainer
import dev.hsuliz.polyyard.service.review.RabbitMQTestcontainer.Companion.rabbitMQContainer
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTest :
    FunSpec({
      test("Context loaded") {}
      test("Postgres test container should be running") {
        postgresContainer.isRunning shouldBe true
      }
      test("Keycloak test container should be running") {
        keycloakContainer.isRunning shouldBe true
      }
      test("RabbitMQ test container should be running") {
        rabbitMQContainer.isRunning shouldBe true
      }
    })
