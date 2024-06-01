package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Book
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, String> {

    fun findBooksByTitle(title: String): Flow<Book>

    suspend fun findBookByIsbn(isbn: String): Book?

    // @Query("{ 'reviews.username': ?0 }")
    // fun findBooksByReviewUsername(username: String): Flow<Book>
}
