package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.component.BookFinder
import dev.hsuliz.bookservice.model.Book
import dev.hsuliz.bookservice.model.Opinion
import dev.hsuliz.bookservice.model.Review
import dev.hsuliz.bookservice.repository.BookRepository
import dev.hsuliz.bookservice.repository.ReviewRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeService(
    private val bookFinder: BookFinder,
    private val bookRepository: BookRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend fun addBook(
        username: String,
        isbn: String,
        dateFinished: Date,
        opinion: Opinion? = null
    ) {
        val book: Book =
            bookRepository.findBookByIsbn(isbn)
                ?: bookFinder.findBookByIsbn(isbn).also { bookRepository.save(it) }
        reviewRepository.save(Review(username, book, opinion))
    }

    fun removeBook(isbn: String) {}
}
