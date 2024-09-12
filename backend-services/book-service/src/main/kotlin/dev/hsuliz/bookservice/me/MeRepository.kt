package dev.hsuliz.bookservice.me

import dev.hsuliz.bookservice.book.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeRepository : CoroutineCrudRepository<Book, String> {

}
