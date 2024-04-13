package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.exception.BookException
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {
    suspend fun saveBook(book: Book) {
        if (bookRepository.existsByTitle(book.title)) {
            throw BookException("Book with title ${book.title} already exists")
        }
        bookRepository.save(book)
    }

    fun getAllBooks(): Flow<Book> = bookRepository.findAll()

    suspend fun findBookByTitle(title: String): Book? {
        val book = bookRepository.findBookByTitle(title)
        return book ?: throw BookException("Book with title $title not found")
    }
}
