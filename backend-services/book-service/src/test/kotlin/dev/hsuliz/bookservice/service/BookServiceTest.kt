package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Review
import dev.hsuliz.bookservice.repository.BookRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat

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

        test("Given book title should be found") {
            // given
            val givenExistingTitle = "Wolf"
            val expected = Book("Wolf", Author("Hesse", "Herman"), Review(5, "Good"))
            coEvery { bookRepository.findBookByTitle(any()) } returns expected

            // when
            val actual = bookService.findBookByTitle(givenExistingTitle)

            // then
            coVerify { bookRepository.findBookByTitle(any()) }
            assertThat(actual).isSameAs(expected)
        }
    })
