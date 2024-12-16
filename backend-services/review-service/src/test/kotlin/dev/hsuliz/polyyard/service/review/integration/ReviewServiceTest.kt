package dev.hsuliz.polyyard.service.review.integration

import com.ninjasquad.springmockk.MockkBean
import dev.hsuliz.polyyard.service.review.PostgresTestContainer
import dev.hsuliz.polyyard.service.review.ReviewService
import dev.hsuliz.polyyard.service.review.util.getCurrentUsername
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockkStatic
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceTest(
    @MockkBean private val reactiveJwtDecoder: ReactiveJwtDecoder,
    private val reviewService: ReviewService
) :
    PostgresTestContainer,
    FunSpec({
      beforeEach {
        mockkStatic("dev.hsuliz.polyyard.service.review.util.SecurityUtilKt")
        coEvery { getCurrentUsername() } returns "Sasha"
      }

      test("Im just a test") {
        val x = reviewService.findReviews()
        x.collect { println(it) }
        3 shouldBe 3
      }
    })
