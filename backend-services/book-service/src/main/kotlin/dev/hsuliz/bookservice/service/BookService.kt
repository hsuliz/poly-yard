package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.exception.BookAlreadyExistsException
import dev.hsuliz.bookservice.exception.BookNotFoundException
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    suspend fun saveBook(book: Book): Book {
        val bookTitle = book.title
        with(bookRepository) {
            if (existsByTitle(bookTitle))
                throw BookAlreadyExistsException("Book with title [$bookTitle] already exists")
            else return save(book)
        }
    }

    suspend fun getBookById(bookId: String): Book {
        if (ObjectId.isValid(bookId)) {
            return bookRepository.findById(bookId)
                ?: throw BookNotFoundException("Book with id:[$bookId] not found")
        } else {
            throw IllegalArgumentException("Id [$bookId] is not valid")
        }
    }

    fun findAllBooks(): Flow<Book> = bookRepository.findAll()

    fun findBooksByTitle(title: String): Flow<Book> = bookRepository.findBooksByTitle(title)

    suspend fun deleteBookById(bookId: String) =
        with(bookRepository) {
            if (existsById(bookId)) deleteById(bookId)
            else throw BookNotFoundException("Book with id:[$bookId] not found")
        }
}
