package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Suppress("SpringDataRepositoryMethodReturnTypeInspection")
interface BookRepository : CoroutineCrudRepository<Book, String> {
    suspend fun findBookByTitle(title: String): Book?

    suspend fun existsByTitle(title: String): Boolean
}
