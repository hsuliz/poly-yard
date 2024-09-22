package dev.hsuliz.bookservice.book

import dev.hsuliz.bookservice.book.model.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, Long> {

  suspend fun existsByIsbn(isbn: String): Boolean

  suspend fun findByIsbn(isbn: String): Book?
}
