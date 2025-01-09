package dev.hsuliz.polyyard.service.book

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface BookRepository :
    CoroutineCrudRepository<Book, Long>, CoroutineSortingRepository<Book, Long> {

  suspend fun existsByIsbn(isbn: String): Boolean

  fun findAllByIsbnIn(isbns: List<String>): Flow<Book>

  suspend fun findByIsbn(isbn: String): Book?
}
