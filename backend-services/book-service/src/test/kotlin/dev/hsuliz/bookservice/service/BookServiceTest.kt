package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Review
import dev.hsuliz.bookservice.repository.BookRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class BookServiceTest {

    @MockK private lateinit var bookRepository: BookRepository
    @InjectMockKs private lateinit var bookService: BookService

    @Test
    fun `Given book should be saved`() = runTest {
        // given
        val givenBook = Book("Wolf", Author("Hesse", "Herman"), Review(5, "Good"))
        coEvery { bookRepository.save(any()) } returns givenBook

        // when
        bookService.saveBook(givenBook)

        // then
        coVerify { bookRepository.save(any()) }
    }

    @Test
    fun `Given book title should be found`() = runTest {
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
}
