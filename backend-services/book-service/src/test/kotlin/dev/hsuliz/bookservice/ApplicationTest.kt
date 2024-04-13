package dev.hsuliz.bookservice

import io.kotest.matchers.shouldBe

class ApplicationTest :
    IntegrationSpec({
        test("Test container should be running") { mongoDBContainer.isRunning shouldBe true }
    })
