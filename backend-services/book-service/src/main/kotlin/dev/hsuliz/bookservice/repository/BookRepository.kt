package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Book
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Suppress("SpringDataRepositoryMethodReturnTypeInspection")
interface BookRepository : CoroutineCrudRepository<Book, String> {

    fun findBooksByTitle(title: String): Flow<Book>

    suspend fun existsByTitle(title: String): Boolean
}
