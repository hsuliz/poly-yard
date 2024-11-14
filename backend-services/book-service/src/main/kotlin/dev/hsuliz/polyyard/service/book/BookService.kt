package dev.hsuliz.polyyard.service.book

import dev.hsuliz.polyyard.service.book.component.BookSearcher
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository,
    private val searcher: BookSearcher,
) {
  suspend fun findAvailableBookToCreate(isbn: String): Book {
    return try {
      searcher.findBookByIsbn(isbn)
    } catch (e: Exception) {
      throw IllegalArgumentException("Book with isbn: $isbn doesn't exists!")
    }
  }

  suspend fun findExistingBook(isbn: String): Book? {
    return repository.findByIsbn(isbn)
  }
}
