package dev.hsuliz.polyyard.service.book

import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTest :
    dev.hsuliz.polyyard.service.book.IntegrationFunSpec({
      test("Test container should be running") { postgresDBContainer.isRunning shouldBe true }
    })
