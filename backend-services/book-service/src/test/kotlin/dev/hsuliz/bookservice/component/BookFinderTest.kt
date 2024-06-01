package dev.hsuliz.bookservice.component

import io.kotest.core.spec.style.FunSpec

class BookFinderTest() :
    FunSpec({
        val bookFinder = BookFinder()

        test("Book by ISBN should be founded") {
            // given
            val givenBookIsbn = "random"

            // when
            bookFinder.findBookByIsbn(givenBookIsbn)
        }
    })
