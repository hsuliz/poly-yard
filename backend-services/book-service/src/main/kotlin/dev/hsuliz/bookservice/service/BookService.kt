package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.exception.BookException
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    suspend fun getBookById(id: String): Book {
        ObjectId.isValid(id)
        return bookRepository.getById(ObjectId(id))
    }

    fun findAllBooks(): Flow<Book> = bookRepository.findAll()

    fun findBooksByTitle(title: String): Flow<Book> {
        return bookRepository.findBooksByTitle(title)
    }

    suspend fun saveBook(book: Book) {
        if (bookRepository.existsByTitle(book.title)) {
            throw BookException("Book with title ${book.title} already exists")
        }
        bookRepository.save(book)
    }
}
