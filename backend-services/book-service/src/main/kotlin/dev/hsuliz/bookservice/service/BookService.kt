package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.exception.BookException
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    suspend fun getBookById(id: String): Book =
        bookRepository.findById(id).takeIf { ObjectId.isValid(id) }
            ?: throw BookException("Book with id: $id does not exists.")

    fun findAllBooks(): Flow<Book> = bookRepository.findAll()

    fun findBooksByTitle(title: String): Flow<Book> = bookRepository.findBooksByTitle(title)

    suspend fun saveBook(book: Book): Book =
        with(bookRepository) {
            if (existsByTitle(book.title)) {
                throw BookException("Book with title ${book.title} already exists")
            }
            save(book)
        }

    suspend fun deleteBookById(id: String) {
        with(bookRepository) {
            if (existsById(id)) deleteById(id)
            else throw BookException("Book with title $id already exists")
        }
    }
}
