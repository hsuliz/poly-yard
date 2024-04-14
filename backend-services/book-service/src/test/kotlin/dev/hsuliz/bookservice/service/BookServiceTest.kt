package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.TestConstants.NON_EXISTING_BOOK
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import dev.hsuliz.bookservice.exception.BookException
import dev.hsuliz.bookservice.repository.BookRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList

internal class BookServiceTest :
    FunSpec({
        lateinit var bookRepository: BookRepository
        lateinit var bookService: BookService

        beforeTest {
            bookRepository = mockk()
            bookService = BookService(bookRepository)
        }

        test("Given book should be saved") {
            // given
            val givenBook = NORMAL_BOOK
            coEvery { bookRepository.existsByTitle(any()) } returns false
            coEvery { bookRepository.save(any()) } returns givenBook

            // when
            bookService.saveBook(givenBook)

            // then
            coVerify { bookRepository.save(any()) }
        }

        test("Given book shouldn't be saved by duplication") {
            // given
            val givenBook = NORMAL_BOOK
            coEvery { bookRepository.existsByTitle(any()) } returns true

            // when&then
            val actual = shouldThrow<BookException> { bookService.saveBook(givenBook) }
            actual.message shouldBe "Book with title ${givenBook.title} already exists"
        }

        test("Given book should be found by title") {
            // given
            val givenExistingTitle = NORMAL_BOOK.title
            val expected = listOf(NORMAL_BOOK)
            coEvery { bookRepository.findBooksByTitle(any()) } returns expected.asFlow()

            // when
            val actual = bookService.findBooksByTitle(givenExistingTitle).toList()

            // then
            coVerify { bookRepository.findBooksByTitle(any()) }
            actual.shouldNotBeEmpty()
            actual shouldBe expected
        }

        test("Given book shouldn't be found by title and throw") {
            // given
            val givenBook = NORMAL_BOOK
            val nonExistingBook = NON_EXISTING_BOOK
            coEvery { bookRepository.findBooksByTitle(any()) } throws
                BookException("Book with title ${givenBook.title} not found")

            // when
            val actual =
                shouldThrow<BookException> { bookService.findBooksByTitle(nonExistingBook.title) }

            // then
            coVerify { bookRepository.findBooksByTitle(any()) }
            actual.message shouldBe "Book with title ${givenBook.title} not found"
        }
    })
