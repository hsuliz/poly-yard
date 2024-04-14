package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.IntegrationFunSpec
import dev.hsuliz.bookservice.TestConstants.NON_EXISTING_BOOK
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class BookRepositoryTest(@Autowired private val bookRepository: BookRepository) :
    IntegrationFunSpec({
        test("Given book should be founded") {
            // given
            val givenExistingBook = NORMAL_BOOK
            val givenNonExistingBook = NON_EXISTING_BOOK
            bookRepository.save(givenExistingBook)

            // when&then
            val actualCorrectBook =
                bookRepository.findBooksByTitle(givenExistingBook.title).toList()

            actualCorrectBook.shouldHaveSize(1)
            actualCorrectBook.first() shouldBe givenExistingBook

            // when&then
            val actualWrongBook =
                bookRepository.findBooksByTitle(givenNonExistingBook.title).toList()
            actualWrongBook.shouldBeEmpty()
        }
    })
