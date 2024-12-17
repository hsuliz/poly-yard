package dev.hsuliz.polyyard.service.review

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface PostgresTestcontainer {
  companion object {
    @Container
    @JvmField
    val postgresContainer =
        PostgreSQLContainer("postgres:16-alpine").apply { withInitScript("init.sql") }

    @DynamicPropertySource
    @JvmStatic
    fun overrideProperties(registry: DynamicPropertyRegistry) {
      registry.add("spring.r2dbc.url") {
        "r2dbc:postgresql://${postgresContainer.host}:${postgresContainer.firstMappedPort}/test"
      }
      registry.add("spring.r2dbc.username") { postgresContainer.username }
      registry.add("spring.r2dbc.password") { postgresContainer.password }
    }

    init {
      postgresContainer.start()
    }
  }
}
