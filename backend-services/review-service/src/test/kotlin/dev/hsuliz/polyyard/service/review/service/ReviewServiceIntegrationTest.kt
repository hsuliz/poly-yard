package dev.hsuliz.polyyard.service.review.service

import com.ninjasquad.springmockk.MockkBean
import dev.hsuliz.polyyard.service.review.PostgresTestcontainer
import dev.hsuliz.polyyard.service.review.RabbitMQTestcontainer
import dev.hsuliz.polyyard.service.review.ReviewService
import dev.hsuliz.polyyard.service.review.component.ReviewCreatedMessage
import dev.hsuliz.polyyard.service.review.exception.ReviewAlreadyExistsException
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.util.currentUsername
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.r2dbc.core.await
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceIntegrationTest(
    private val reviewService: ReviewService,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val amqpAdmin: AmqpAdmin,
    private val rabbitTemplate: RabbitTemplate,
    @MockkBean private val reactiveAuditorAware: ReactiveAuditorAware<String>,
    @MockkBean private val reactiveJwtDecoder: ReactiveJwtDecoder // mocked Keycloak resource server
) :
    PostgresTestcontainer,
    RabbitMQTestcontainer,
    FunSpec({
      beforeEach { cleanAndPopulateDatabase(r2dbcEntityTemplate) }

      test("Should save review for new resource and send it to MQ") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }
        mockkStatic("dev.hsuliz.polyyard.service.review.util.SecurityUtilsKt")
        every { reactiveAuditorAware.currentAuditor } returns Mono.just("Sasha")
        coEvery { currentUsername() } returns "Sasha"

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
        mockkStatic("dev.hsuliz.polyyard.service.review.util.SecurityUtilsKt")
        every { reactiveAuditorAware.currentAuditor } returns Mono.just("Sasha")
        coEvery { currentUsername() } returns "Sasha"

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
        mockkStatic("dev.hsuliz.polyyard.service.review.util.SecurityUtilsKt")
        every { reactiveAuditorAware.currentAuditor } returns Mono.just("user1")
        coEvery { currentUsername() } returns "user1"

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
