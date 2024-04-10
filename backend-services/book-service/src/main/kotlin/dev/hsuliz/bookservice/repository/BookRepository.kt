package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CoroutineCrudRepository<Book, String> {
    suspend fun findBookByTitle(title: String): Book?
}
