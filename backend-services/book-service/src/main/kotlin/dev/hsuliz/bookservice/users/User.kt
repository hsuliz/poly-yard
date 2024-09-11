package dev.hsuliz.bookservice.users

import dev.hsuliz.bookservice.books.Book
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id var id: Long? = null,
    val username: String,
    @Transient val books: List<Book> = mutableListOf(),
)
