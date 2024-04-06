package dev.hsuliz.bookservice.repository

import dev.hsuliz.bookservice.model.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, String>
