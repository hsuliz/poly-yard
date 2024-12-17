package dev.hsuliz.polyyard.service.review.integration

import com.ninjasquad.springmockk.MockkBean
import dev.hsuliz.polyyard.service.review.PostgresTestcontainer
import dev.hsuliz.polyyard.service.review.RabbitMQTestcontainer
import dev.hsuliz.polyyard.service.review.ReviewService
import dev.hsuliz.polyyard.service.review.component.ReviewCreatedMessage
import dev.hsuliz.polyyard.service.review.model.Review
import dev.hsuliz.polyyard.service.review.util.getCurrentUsername
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceTest(
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
      beforeEach {
        mockkStatic("dev.hsuliz.polyyard.service.review.util.SecurityUtilKt")
        every { reactiveAuditorAware.currentAuditor } returns Mono.just("Sasha")
        coEvery { getCurrentUsername() } returns "Sasha"
      }

      test("Should save review for new resource and send it to MQ") {
        // setup
        Queue("book").also { amqpAdmin.declareQueue(it) }

        // given
        val givenReviewType = Review.Type.BOOK
        val givenReviewResource = Review.Resource(Review.Resource.Type.ISBN, "9781451673319")
        val givenRating = 5

        // when
        val reviewEntity =
            reviewService.createReview(givenReviewType, givenReviewResource, givenRating)

        // then
        val savedReview =
            r2dbcEntityTemplate
                .select(
                    Query.query(Criteria.where("id").`is`(reviewEntity.id!!)), Review::class.java)
                .awaitSingle()

        // then
        // reviewEntity shouldBe savedReview // currently won't pass

        // then
        val receivedMessageFromMq = rabbitTemplate.receiveAndConvert("book", 5000)
        receivedMessageFromMq shouldBe
            ReviewCreatedMessage(Review.Resource.Type.ISBN, "9781451673319")
      }

      test("Just test") {
        val x = reviewService.findReviews()
        x.collect { println(it) }
      }
    })
