package dev.hsuliz.bookservice.book

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, Long> {

  suspend fun existsByIsbn(isbn: String): Boolean

  suspend fun findByIsbn(isbn: String): Book?
}
