package dev.hsuliz.polyyard.service.review

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface PostgresTestContainer {

  companion object {
    @Container
    @ServiceConnection
    @JvmField
    val postgresContainer =
        PostgreSQLContainer("postgres:16-alpine").apply { withInitScript("init.sql") }

    init {
      postgresContainer.start()
    }
  }
}
