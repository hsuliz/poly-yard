package dev.hsuliz.polyyard.service.book

import dev.hsuliz.polyyard.service.book.component.BookSearcher
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository,
    private val searcher: BookSearcher,
) {

  fun findBooksByIsbn(isbnFlow: List<String>): Flow<Book> {
    return repository.findAllByIsbnIn(isbnFlow)
  }

  suspend fun countBooks(): Long {
    return repository.count()
  }

  suspend fun findAvailableBookToCreate(isbn: String): Book {
    return try {
      searcher.findBookBy(isbn)
    } catch (e: Exception) {
      throw IllegalArgumentException("Book with isbn: $isbn doesn't exists!")
    }
  }

  suspend fun findExistingBook(isbn: String): Book? {
    return repository.findByIsbn(isbn)
  }
}
