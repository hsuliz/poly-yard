package dev.hsuliz.bookservice.book

import dev.hsuliz.bookservice.book.component.BookSearcher
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository,
    private val bookSearcher: BookSearcher,
) {

  suspend fun createBook(bookIsbn: String): Book? {
    if (repository.existsByIsbn(bookIsbn)) {
      return null
    }
    val book = bookSearcher.findBookByIsbn(bookIsbn)
    return repository.save(book)
  }

  suspend fun findBookByIsbn(bookIsbn: String): Book? {
    return repository.findByIsbn(bookIsbn)
  }
}
