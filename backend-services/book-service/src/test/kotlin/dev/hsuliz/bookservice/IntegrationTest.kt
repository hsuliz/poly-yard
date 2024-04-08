package dev.hsuliz.bookservice

import dev.hsuliz.bookservice.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Testcontainers
class IntegrationTest(@Autowired val bookRepository: BookRepository) {

    companion object {
        @Container
        @ServiceConnection
        var mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
    }
}
