package dev.hsuliz.bookservice

import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTest :
    IntegrationFunSpec({
      test("Test container should be running") { postgresDBContainer.isRunning shouldBe true }
    })
