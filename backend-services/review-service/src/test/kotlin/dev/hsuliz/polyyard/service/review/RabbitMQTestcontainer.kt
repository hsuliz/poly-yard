package dev.hsuliz.polyyard.service.review

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.junit.jupiter.Container

interface RabbitMQTestcontainer {
  companion object {
    @Container
    @ServiceConnection
    @JvmField
    val rabbitMQContainer = RabbitMQContainer("rabbitmq:4.0.0-management-alpine")

    init {
      rabbitMQContainer.start()
    }
  }
}
