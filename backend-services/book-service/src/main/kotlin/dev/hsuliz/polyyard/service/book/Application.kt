package dev.hsuliz.polyyard.service.book

import dev.hsuliz.polyyard.service.book.component.BookSearcher
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}

@Component
class ReviewStartupRunner(
    private val searcher: BookSearcher,
    private val repository: BookRepository
) : CommandLineRunner {
  override fun run(vararg args: String?) {
    runBlocking {
      //repository.save(searcher.findBookBy("9781451648546"))
      //repository.save(searcher.findBookBy("9780143126560"))
//
      //repository.save(searcher.findBookBy("9780062498533"))
      //repository.save(searcher.findBookBy("9780385514231"))
      /*service.createReview(
        "sasha",
        Review.Type.BOOK,
        Resource.Type.ISBN,
        "dsds",
        5
      )*/
      // service.findReviews(0).collect { println(it) }
      // println("=====")
      // service.findReviews(1).collect { println(it) }
    }
  }
}
