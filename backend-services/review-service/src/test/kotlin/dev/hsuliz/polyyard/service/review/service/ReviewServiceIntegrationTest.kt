package dev.hsuliz.polyyard.service.review.service

import com.fasterxml.jackson.annotation.JsonProperty
import dev.hsuliz.polyyard.service.review.*
import dev.hsuliz.polyyard.service.review.KeycloakTestcontainer.Companion.keycloakAdminClient
import dev.hsuliz.polyyard.service.review.component.ReviewCreatedMessage
import dev.hsuliz.polyyard.service.review.dto.ReviewRequest
import dev.hsuliz.polyyard.service.review.dto.ReviewResponse
import dev.hsuliz.polyyard.service.review.exception.ReviewAlreadyExistsException
import dev.hsuliz.polyyard.service.review.model.Review
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.r2dbc.core.await
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceIntegrationTest(
    private val webTestClient: WebTestClient,
    private val reviewService: ReviewService,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val amqpAdmin: AmqpAdmin,
    private val rabbitTemplate: RabbitTemplate,
    private val oAuth2ResourceServerProperties: OAuth2ResourceServerProperties
) :
    PostgresTestcontainer,
    RabbitMQTestcontainer,
    KeycloakTestcontainer,
    FunSpec({
      beforeSpec { populateKeycloakUsers() }
      beforeEach { cleanAndPopulateDatabase(r2dbcEntityTemplate) }

      test("Should save review for new resource") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }
        val token = token(oAuth2ResourceServerProperties.jwt.issuerUri, "user1")

        // given
        val givenReview =
            ReviewRequest(
                Review.Type.BOOK,
                ReviewRequest.ResourceRequest(Review.Resource.Type.ISBN, NON_EXISTING_ISBN_VALUE),
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
                .select(Query.query(Criteria.where("id").`is`(responseBody.id)), Review::class.java)
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

      test("Should save review for new resource and send it to") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }

        // given
        val givenReviewType = Review.Type.BOOK
        val givenReviewResource = Review.Resource(Review.Resource.Type.ISBN, "9780415217866")
        val givenRating = 5

        // when
        val review = reviewService.createReview(givenReviewType, givenReviewResource, givenRating)

        // then
        val savedReview =
            r2dbcEntityTemplate
                .select(Query.query(Criteria.where("id").`is`(review.id!!)), Review::class.java)
                .awaitSingle()

        review shouldBe savedReview

        // then
        val receivedMessageFromMq = rabbitTemplate.receiveAndConvert("book", 5000)
        receivedMessageFromMq shouldBe
            ReviewCreatedMessage(Review.Resource.Type.ISBN, "9780415217866")
      }

      test("Should save review for already existing resource") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }
        // given
        val givenReviewType = Review.Type.BOOK
        val givenReviewResource = Review.Resource(Review.Resource.Type.ISBN, "9781451673319")
        val givenRating = 5

        // when
        val review = reviewService.createReview(givenReviewType, givenReviewResource, givenRating)

        // then
        val savedReview =
            r2dbcEntityTemplate
                .select(Query.query(Criteria.where("id").`is`(review.id!!)), Review::class.java)
                .awaitSingle()

        review shouldBe savedReview

        // then
        val receivedMessageFromMq = rabbitTemplate.receiveAndConvert("book", 5000)
        receivedMessageFromMq shouldBe
            ReviewCreatedMessage(Review.Resource.Type.ISBN, "9781451673319")
      }

      test("Should throw for review which already exists for current user") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }

        // given
        val givenReviewType = Review.Type.BOOK
        val givenReviewResource = Review.Resource(Review.Resource.Type.ISBN, "9783161484100")
        val givenRating = 5

        // when
        val exception =
            shouldThrowExactly<ReviewAlreadyExistsException> {
              reviewService.createReview(givenReviewType, givenReviewResource, givenRating)
            }

        // then
        exception shouldBe ReviewAlreadyExistsException()
      }

      test("Just test") {
        val x = reviewService.findReviews()
        x.toList().forEach { println(it) }
      }
    }) {
  override fun extensions() = listOf(SpringExtension)
}

private suspend fun token(authServerUrl: String, username: String): String {
  val body =
      BodyInserters.fromFormData("grant_type", "password")
          .with("client_id", "back-end-client")
          .with("client_secret", "**********")
          .with("username", username)
          .with("password", "password123")

  val token =
      WebClient.create()
          .post()
          .uri("$authServerUrl/protocol/openid-connect/token")
          .body(body)
          .retrieve()
          .bodyToMono(KeyCloakToken::class.java)
          .awaitSingle()

  return token.accessToken
}

data class KeyCloakToken(@JsonProperty("access_token") val accessToken: String)

private fun populateKeycloakUsers() {
  keycloakAdminClient
      .realm("polyyard")
      .users()
      .create(
          UserRepresentation().apply {
            username = "user1"
            isEnabled = true
            email = "testuser@example.com"
            firstName = "Test"
            lastName = "User"
            credentials =
                listOf(
                    CredentialRepresentation().apply {
                      type = CredentialRepresentation.PASSWORD
                      value = "password123"
                      isTemporary = false
                    })
          })
}

private suspend fun cleanAndPopulateDatabase(r2dbcEntityTemplate: R2dbcEntityTemplate) {
  r2dbcEntityTemplate.databaseClient
      .sql(
          """
                DELETE FROM reviews CASCADE
            """)
      .await()
  r2dbcEntityTemplate.databaseClient
      .sql(
          """
                DELETE FROM resources CASCADE
            """)
      .await()

  r2dbcEntityTemplate.databaseClient.sql("ALTER SEQUENCE reviews_id_seq RESTART WITH 1").await()
  r2dbcEntityTemplate.databaseClient.sql("ALTER SEQUENCE resources_id_seq RESTART WITH 1").await()

  r2dbcEntityTemplate.databaseClient
      .sql(
          """
            INSERT INTO resources (type, value) VALUES
            ('ISBN', '9783161484100'),
            ('ISBN', '9780306406157'),
            ('ISBN', '9781451673319'),
            ('ISBN', '9780140449136')
         """)
      .await()

  r2dbcEntityTemplate.databaseClient
      .sql(
          """
            INSERT INTO reviews (username, type, resource_id, rating, comment) VALUES
            ('user1', 'BOOK', 1, 5, 'Fantastic book! A must-read.'),
            ('user2', 'BOOK', 2, 4, 'Great concepts but a bit verbose.'),
            ('user3', 'BOOK', 3, 5, 'Incredible storytelling.'),
            ('user4', 'BOOK', 4, 3, 'Good, but not my style.')
         """)
      .await()
}
