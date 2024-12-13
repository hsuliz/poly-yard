package dev.hsuliz.polyyard.service.book

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
abstract class IntegrationFunSpec(body: FunSpec.() -> Unit = {}) : FunSpec(body) {

  companion object {
    @Container
    @ServiceConnection
    @JvmField
    var postgresDBContainer = PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))

    init {
      dev.hsuliz.polyyard.service.book.IntegrationFunSpec.Companion.postgresDBContainer.start()
    }
  }
}
