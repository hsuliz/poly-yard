package dev.hsuliz.bookservice.books

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BooksRepository : CoroutineCrudRepository<Book, Long> {


}
