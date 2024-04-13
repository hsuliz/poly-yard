package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.IntegrationFunSpec
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class BookRepositoryTest(@Autowired bookRepository: BookRepository) :
    IntegrationFunSpec({
        test("Given book should be founded") {
            // given
            val givenBook = NORMAL_BOOK
            bookRepository.save(givenBook)

            // when&then
            val actualCorrectBook = bookRepository.findBookByTitle(NORMAL_BOOK.title)
            actualCorrectBook shouldBe givenBook

            // when&then
            val actualWrongBook = bookRepository.findBookByTitle("Shrek")
            actualWrongBook shouldBe null
        }
    })
