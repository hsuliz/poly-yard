package dev.hsuliz.bookservice.me

import dev.hsuliz.bookservice.book.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeRepository : CoroutineCrudRepository<Book, Long> {
    override suspend fun findById(id: Long): Book?

    suspend fun findByIsbn(isbn: String): Book?
}
