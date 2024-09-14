package dev.hsuliz.bookservice.book.component

import dev.hsuliz.bookservice.config.WebClientConfig
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.FunSpec

class BookSearcherTest :
    FunSpec({
      lateinit var bookSearcher: BookSearcher
      val bookInfoClient = WebClientConfig().bookInfoClient()

      beforeTest { bookSearcher = BookSearcher(bookInfoClient) }

      test("findBookByIsbn") {
        val x = bookSearcher.findBookByIsbn("9781564149305")
          println(x.toString())
      }
    })
