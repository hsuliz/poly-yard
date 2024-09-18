package dev.hsuliz.bookservice.book.component

import dev.hsuliz.bookservice.config.WebClientConfig
import io.kotest.core.spec.style.FunSpec

class BookSearcherTest :
    FunSpec({
      lateinit var bookSearcher: BookSearcher
      val bookInfoClient = WebClientConfig().bookInfoClient()

      beforeTest { bookSearcher = BookSearcher(bookInfoClient) }

      test("findBookByIsbn") {
        val x =
            try {
              bookSearcher.findBookByIsbn("9781234149355")
            } catch (e: Exception) {
                println("Book not found")
            }
        println(x.toString())
      }
    })
