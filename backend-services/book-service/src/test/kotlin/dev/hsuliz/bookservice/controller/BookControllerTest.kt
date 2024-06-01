/*
package dev.hsuliz.bookservice.controller

import com.ninjasquad.springmockk.MockkBean
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK
import dev.hsuliz.bookservice.TestConstants.NORMAL_BOOK_REQUEST
import dev.hsuliz.bookservice.dao.BookResponse
import dev.hsuliz.bookservice.exception.BookAlreadyExistsException
import dev.hsuliz.bookservice.exception.BookNotFoundException
import dev.hsuliz.bookservice.service.BookService
import io.kotest.core.spec.style.FunSpec
import io.mockk.coEvery
import org.bson.types.ObjectId
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(BookController::class)
class BookControllerTest(
    private val webClient: WebTestClient,
    @MockkBean private var bookService: BookService
) :
    FunSpec({
        context("Create book") {
            val request =
                webClient
                    .post()
                    .uri(BOOK_URI)
                    .bodyValue(NORMAL_BOOK_REQUEST)
                    .accept(MediaType.APPLICATION_JSON)

            test("Should save") {
                val givenBook = NORMAL_BOOK
                coEvery { bookService.saveBook(any()) } returns givenBook

                request
                    .exchange()
                    .expectStatus()
                    .isEqualTo(CREATED)
                    .expectBody<BookResponse>()
                    .isEqualTo(givenBook.toResponse())
            }

            test("Should not save") {
                val givenBook = NORMAL_BOOK
                coEvery { bookService.saveBook(any()) } throws
                    BookAlreadyExistsException(
                        "Book with title [${givenBook.title}] already exists"
                    )

                request.exchange().expectStatus().isEqualTo(CONFLICT).expectBody<ProblemDetail>()
            }
        }
        context("Get book by id") {
            val request = { bookId: String ->
                webClient.get().uri("$BOOK_URI/{id}", bookId).accept(MediaType.APPLICATION_JSON)
            }

            test("Should return") {
                val givenBook = NORMAL_BOOK
                val givenBookId = ObjectId.get().toHexString()
                coEvery { bookService.getBookById(any()) } returns givenBook

                request(givenBookId)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(OK)
                    .expectBody<BookResponse>()
                    .isEqualTo(givenBook.toResponse())
            }

            test("Should not found") {
                val givenBookId = ObjectId.get().toHexString()

                coEvery { bookService.getBookById(any()) } throws
                    BookNotFoundException("Book with id:[$givenBookId] not found")

                request(givenBookId)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(NOT_FOUND)
                    .expectBody<ProblemDetail>()
            }

            test("Should not accept wrong id") {
                val givenBookId = ObjectId.get().toHexString()
                coEvery { bookService.getBookById(any()) } throws
                    IllegalArgumentException("Id [$givenBookId] is not valid")

                request(givenBookId)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(UNPROCESSABLE_ENTITY)
                    .expectBody<ProblemDetail>()
            }
        }
    })

const val BOOK_URI = "/api/book"
*/
