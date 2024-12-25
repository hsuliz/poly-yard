package dev.hsuliz.polyyard.service.review

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
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

    fun cleanAndPopulateDatabase(r2dbcEntityTemplate: R2dbcEntityTemplate) {
      listOf(
              "DELETE FROM reviews CASCADE",
              "DELETE FROM resources CASCADE",
              "ALTER SEQUENCE reviews_id_seq RESTART WITH 1",
              "ALTER SEQUENCE resources_id_seq RESTART WITH 1",
              """
        INSERT INTO resources (type, value) VALUES
        ('ISBN', '9783161484100'),
        ('ISBN', '9780306406157'),
        ('ISBN', '9781451673319'),
        ('ISBN', '9780140449136')
    """,
              """
        INSERT INTO reviews (username, type, resource_id, rating, comment) VALUES
        ('user1', 'BOOK', 1, 5, 'Fantastic book! A must-read.'),
        ('user2', 'BOOK', 2, 4, 'Great concepts but a bit verbose.'),
        ('user3', 'BOOK', 3, 5, 'Incredible storytelling.'),
        ('user4', 'BOOK', 4, 3, 'Good, but not my style.')
    """)
          .map { query -> r2dbcEntityTemplate.databaseClient.sql(query).fetch().rowsUpdated() }
          .reduce { acc, next -> acc.then(next) }
          .block()
    }
  }
}
