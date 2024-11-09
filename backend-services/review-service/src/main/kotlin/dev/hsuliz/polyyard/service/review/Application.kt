package dev.hsuliz.polyyard.service.review

import dev.hsuliz.polyyard.service.review.entity.Resource
import dev.hsuliz.polyyard.service.review.entity.Review
import dev.hsuliz.polyyard.service.review.repository.ReviewRepository
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
class ReviewStartupRunner(private val service: ReviewService,
  private val repo: ReviewRepository) : CommandLineRunner {
  override fun run(vararg args: String?) {
    runBlocking {

      service.createReview(
        "sasha",
        Review.Category.BOOK,
        Pair(Resource.Type.ISBN, "dsds"),
        5
      )
      //service.findReviews(0).collect { println(it) }
      //println("=====")
      //service.findReviews(1).collect { println(it) }
    }
  }
}
