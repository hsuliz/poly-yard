package dev.hsuliz.bookservice

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Testcontainers
abstract class IntegrationSpec(body: FunSpec.() -> Unit = {}) : FunSpec(body) {

    companion object {
        @Container
        @ServiceConnection
        @JvmField
        var mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:7.0"))

        init {
            mongoDBContainer.start()
        }
    }
}
