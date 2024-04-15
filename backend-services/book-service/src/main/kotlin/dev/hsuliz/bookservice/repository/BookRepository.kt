package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Book
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Suppress("SpringDataRepositoryMethodReturnTypeInspection")
interface BookRepository : CoroutineCrudRepository<Book, String> {

    suspend fun getById(id: ObjectId): Book

    fun findBooksByTitle(title: String): Flow<Book>

    suspend fun existsByTitle(title: String): Boolean
}
