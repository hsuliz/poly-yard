package dev.hsuliz.bookservice.service

import dev.hsuliz.bookservice.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {}
