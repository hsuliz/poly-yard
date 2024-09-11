package dev.hsuliz.bookservice.books

import org.springframework.stereotype.Service

@Service
class BookService(private val repository: BookRepository) {

    suspend fun findBookById(bookId: Long): Book? {
        return repository.findById(bookId)
    }
}
