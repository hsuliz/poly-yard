package dev.hsuliz.polyyard.service.review.service

import dev.hsuliz.polyyard.service.review.*
import dev.hsuliz.polyyard.service.review.KeycloakTestcontainer.Companion.fetchToken
import dev.hsuliz.polyyard.service.review.PostgresTestcontainer.Companion.cleanAndPopulateDatabase
import dev.hsuliz.polyyard.service.review.component.ReviewCreatedMessage
import dev.hsuliz.polyyard.service.review.dto.ReviewRequest
import dev.hsuliz.polyyard.service.review.dto.ReviewResponse
import dev.hsuliz.polyyard.service.review.model.Review
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceIntegrationTest(
    private val webTestClient: WebTestClient,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val amqpAdmin: AmqpAdmin,
    private val rabbitTemplate: RabbitTemplate
) :
    PostgresTestcontainer,
    RabbitMQTestcontainer,
    KeycloakTestcontainer,
    FunSpec({
      beforeEach { cleanAndPopulateDatabase(r2dbcEntityTemplate) }

      context("should save review") {
        withData(
            mapOf(
                "non existing isbn resource" to Pair("user1", NON_EXISTING_ISBN_VALUE),
                "existing isbn resource" to
                    Pair("user1", EXISTING_ISBN_VALUE_USER_2), // comma because formatter goes wild
            )) { (user1, isbnValue) ->
              // setup
              Queue("book").also { amqpAdmin.declareQueue(it) }
              val token = fetchToken(user1)

              // given
              val givenReview =
                  ReviewRequest(
                      Review.Type.BOOK,
                      ReviewRequest.ResourceRequest(Review.Resource.Type.ISBN, isbnValue),
                      5,
                      "Good")

              // when
              val response =
                  webTestClient
                      .post()
                      .uri("/api/me/reviews")
                      .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                      .bodyValue(givenReview)
                      .exchange()
                      .returnResult<ReviewResponse>()

              // then
              response.status shouldBe HttpStatusCode.valueOf(201)

              val responseBody = response.responseBody.blockFirst()!!
              val savedReview =
                  r2dbcEntityTemplate
                      .select(
                          Query.query(Criteria.where("id").`is`(responseBody.id)),
                          Review::class.java)
                      .blockFirst()!!

              responseBody.asClue {
                it.type shouldBe savedReview.type
                it.id shouldBe savedReview.id
                it.rating shouldBe savedReview.rating
                it.comment shouldBe savedReview.comment
                it.username shouldBe savedReview.username
                it.resource shouldBe savedReview.resource
                it.createdAt shouldBe savedReview.createdAt
              }

              val receivedMessageFromMq = rabbitTemplate.receiveAndConvert("book", 5000)
              withClue("Should send message to MQ") {
                receivedMessageFromMq shouldBe
                    ReviewCreatedMessage(givenReview.resource.type, givenReview.resource.value)
              }
            }
      }

      test("Should throw for review which already exists for current user") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }
        val token = fetchToken("user1")

        // given
        val givenReview =
            ReviewRequest(
                Review.Type.BOOK,
                ReviewRequest.ResourceRequest(
                    Review.Resource.Type.ISBN, EXISTING_ISBN_VALUE_USER_1),
                5,
                "Good")

        // when & then
        webTestClient
            .post()
            .uri("/api/me/reviews")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .bodyValue(givenReview)
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.CONFLICT)
      }
    }) {
  override fun extensions() = listOf(SpringExtension)
}
