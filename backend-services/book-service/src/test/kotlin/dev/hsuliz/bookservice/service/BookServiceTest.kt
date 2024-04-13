package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.TestConstants.NON_EXISTING_BOOK
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import dev.hsuliz.bookservice.exception.BookException
import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Review
import dev.hsuliz.bookservice.repository.BookRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class BookServiceTest :
    FunSpec({
        lateinit var bookRepository: BookRepository
        lateinit var bookService: BookService

        beforeTest {
            bookRepository = mockk()
            bookService = BookService(bookRepository)
        }

        test("Given book should be saved") {
            // given
            val givenBook = Book("Wolf", Author("Hesse", "Herman"), Review(5, "Good"))
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

            // when
            val actual = shouldThrow<BookException> { bookService.saveBook(givenBook) }

            // then
            actual.message shouldBe "Book with title ${givenBook.title} already exists"
        }

        test("Given book should be found by title") {
            // given
            val givenExistingTitle = "Wolf"
            val expected = Book("Wolf", Author("Hesse", "Herman"), Review(5, "Good"))
            coEvery { bookRepository.findBookByTitle(any()) } returns expected

            // when
            val actual = bookService.findBookByTitle(givenExistingTitle)

            // then
            coVerify { bookRepository.findBookByTitle(any()) }
            actual shouldBe expected
        }

        test("Given book shouldn't be found by title and throw") {
            // given
            val givenBook = NORMAL_BOOK
            val nonExistingBook = NON_EXISTING_BOOK
            coEvery { bookRepository.findBookByTitle(any()) } throws
                BookException("Book with title ${givenBook.title} not found")

            // when
            val actual =
                shouldThrow<BookException> { bookService.findBookByTitle(nonExistingBook.title) }

            // then
            coVerify { bookRepository.findBookByTitle(any()) }
            actual.message shouldBe "Book with title ${givenBook.title} not found"
        }
    })
