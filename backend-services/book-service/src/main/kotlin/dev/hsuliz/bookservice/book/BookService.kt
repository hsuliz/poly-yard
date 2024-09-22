package dev.hsuliz.bookservice.book

import dev.hsuliz.bookservice.book.component.BookSearcher
import dev.hsuliz.bookservice.book.model.Book
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository,
    private val searcher: BookSearcher,
) {

  suspend fun createBook(isbn: String): Book {
    if (repository.existsByIsbn(isbn))
        throw IllegalArgumentException("Book with isbn: $isbn is already exists!")

    return repository.save(findAvailableBookToCreate(isbn))
  }

  suspend fun findAvailableBookToCreate(isbn: String): Book {
    return try {
      searcher.findBookByIsbn(isbn)
    } catch (e: Exception) {
      throw IllegalArgumentException("Book with isbn: $isbn doesnt exists!")
    }
  }

  suspend fun findExistingBook(isbn: String): Book? {
    return repository.findByIsbn(isbn)
  }
}
