package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.exception.BookNotFoundException
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    suspend fun getBookById(id: String): Book {
        if (ObjectId.isValid(id)) {
            return bookRepository.findById(id) ?: throw BookNotFoundException(id)
        } else {
            throw IllegalArgumentException("Id is not valid.")
        }
    }

    fun findAllBooks(): Flow<Book> = bookRepository.findAll()

    fun findBooksByTitle(title: String): Flow<Book> = bookRepository.findBooksByTitle(title)

    suspend fun saveBook(book: Book): Book {
        val bookTitle = book.title
        with(bookRepository) {
            if (existsByTitle(bookTitle)) throw BookNotFoundException(bookTitle)
            else return save(book)
        }
    }

    suspend fun deleteBookById(bookId: String) =
        with(bookRepository) {
            if (existsById(bookId)) deleteById(bookId) else throw BookNotFoundException(bookId)
        }
}
