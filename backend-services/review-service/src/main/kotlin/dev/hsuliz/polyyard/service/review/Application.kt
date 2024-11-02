package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.model.ReviewType
import dev.hsuliz.polyyard.service.review.service.ReviewService
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
class ReviewStartupRunner(private val service: ReviewService) : CommandLineRunner {
  override fun run(vararg args: String?) {
    runBlocking {
      service.createReview("Sasha", ReviewType("book", 234), 3)
      //service.findReviews(0).collect { println(it) }
      //println("=====")
      //service.findReviews(1).collect { println(it) }
    }
  }
}
