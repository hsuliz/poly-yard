/* probably useless tests but whatever */

package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.IntegrationFunSpec
import dev.hsuliz.bookservice.TestConstants.NON_EXISTING_BOOK
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import io.kotest.assertions.asClue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class BookRepositoryTest(@Autowired private val bookRepository: BookRepository) :
    IntegrationFunSpec({
        test("Book should be found by id") {
            // given
            val givenExistingBook = bookRepository.save(NORMAL_BOOK)
            val givenNonExistingBook = NON_EXISTING_BOOK

            // when&then
            val actualCorrectBook = bookRepository.findById(givenExistingBook.id.toHexString())
            actualCorrectBook shouldBe givenExistingBook

            // when&then
            val actualWrongBook = bookRepository.findById(givenNonExistingBook.id.toHexString())
            actualWrongBook.asClue { it shouldBe null }
        }

        test("Books should be found by title") {
            // given
            val givenExistingBook = bookRepository.save(NORMAL_BOOK)
            val givenNonExistingBook = NON_EXISTING_BOOK

            // when&then
            val actualExistingBook =
                bookRepository.findBooksByTitle(givenExistingBook.title).toList()
            actualExistingBook.shouldHaveSize(1)
            actualExistingBook.first() shouldBe givenExistingBook

            // when&then
            val actualWrongBook =
                bookRepository.findBooksByTitle(givenNonExistingBook.title).toList()
            actualWrongBook.shouldBeEmpty()
        }
    })
