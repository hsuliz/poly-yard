package dev.hsuliz.bookservice.controller

import com.ninjasquad.springmockk.MockkBean
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK_REQUEST
import dev.hsuliz.bookservice.dao.BookResponse
import dev.hsuliz.bookservice.exception.BookAlreadyExistsException
import dev.hsuliz.bookservice.service.BookService
import io.kotest.core.spec.style.FunSpec
import io.mockk.coEvery
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(BookController::class)
class BookControllerTest(
    private val webClient: WebTestClient,
    @MockkBean private var bookService: BookService
) :
    FunSpec({
        context("Create book") {
            test("Should save") {
                val givenBook = NORMAL_BOOK
                coEvery { bookService.saveBook(any()) } returns givenBook
                webClient
                    .post()
                    .uri(BOOK_URI)
                    .bodyValue(NORMAL_BOOK_REQUEST)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated
                    .expectBody(BookResponse::class.java)
                    .isEqualTo(givenBook.toResponse())
            }

            test("Should not save") {
                val givenBook = NORMAL_BOOK
                coEvery { bookService.saveBook(any()) } throws
                    BookAlreadyExistsException(
                        "Book with title [${givenBook.title}] already exists"
                    )
                webClient
                    .post()
                    .uri(BOOK_URI)
                    .bodyValue(NORMAL_BOOK_REQUEST)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.CONFLICT)
                    .expectBody(ProblemDetail::class.java)
            }
        }
    })

const val BOOK_URI = "/api/book"
