package dev.hsuliz.polyyard.service.book.component

import dev.hsuliz.polyyard.service.book.Book
import dev.hsuliz.polyyard.service.book.config.WebClientConfig
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BookSearcherTest :
    FunSpec({
      lateinit var bookSearcher: BookSearcher

      beforeTest { bookSearcher = BookSearcher(WebClientConfig().bookInfoClient()) }

      test("Given ISBN finds book") {
        // given
        val givenISBN = "9780872203495"

        // when
        val result = bookSearcher.findBookBy(givenISBN)

        // then
        result shouldBe
            Book(
                "9780872203495",
                "Complete Works",
                "Plato",
                1997,
                1852,
                "http://books.google.com/books/content?id=eSKTvJDrr5kC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                null,
            )
      }

      test("Given non existing ISBN should throw") {
        // given
        val givenISBN = "9780872256495"

        // when
        val exception =
            shouldThrow<IllegalStateException> { bookSearcher.findBookBy(givenISBN) }

        // then
        exception.message shouldBe "Failed to map the response to BookMapper for isbn: $givenISBN"
      }
    })
