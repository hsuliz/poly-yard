package dev.hsuliz.bookservice.service

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

        test("Book should be saved") {
            // given
            val givenBook = NORMAL_BOOK
            coEvery { bookRepository.existsByTitle(any()) } returns false
            coEvery { bookRepository.save(any()) } returns givenBook

            // when
            bookService.saveBook(givenBook)

            // then
            coVerify { bookRepository.save(any()) }
        }

        test("Book shouldn't be saved by duplication then throw") {
            // given
            val givenBook = NORMAL_BOOK
            coEvery { bookRepository.existsByTitle(any()) } returns true

            // when&then
            val actual = shouldThrow<BookException> { bookService.saveBook(givenBook) }
            actual.message shouldBe "Book with title [${givenBook.title}] already exists"
        }

        test("Book should be get by id") {
            // given
            val givenBook = NORMAL_BOOK
            coEvery { bookRepository.findById(any()) } returns givenBook

            // when&then
            val actual = bookService.getBookById(givenBook.id.toHexString())
            actual shouldBe givenBook
        }

        test("Book shouldn't be get by id") {
            // given
            val givenBook = NORMAL_BOOK
            coEvery { bookRepository.findById(any()) } returns null

            // when&then
            val actual =
                shouldThrow<BookException> { bookService.getBookById(givenBook.id.toHexString()) }
            actual.message shouldBe "Book with id:[${givenBook.id}] not found"
        }

        test("Book should be found by title") {
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
    })
