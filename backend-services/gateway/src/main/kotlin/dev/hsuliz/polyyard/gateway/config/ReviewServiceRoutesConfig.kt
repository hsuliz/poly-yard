package dev.hsuliz.polyyard.gateway.config

import dev.hsuliz.polyyard.gateway.filter.ReviewRequestChecker
import dev.hsuliz.polyyard.gateway.filter.ReviewResponseRewriteFunction
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod

@Configuration
class ReviewServiceRoutesConfig(
    @Value("\${review-service-host}") private val reviewServiceHost: String
) {
  @Bean
  fun routes(
      routeLocatorBuilder: RouteLocatorBuilder,
      rewriteFunction: ReviewResponseRewriteFunction,
      reviewRequestChecker: ReviewRequestChecker
  ): RouteLocator =
      routeLocatorBuilder.routes {
        route("reviews-aggregation") {
          uri(reviewServiceHost)
          path("/api/reviews/**")
          method(HttpMethod.GET)
          filters {
            modifyResponseBody(ByteArray::class.java, ByteArray::class.java, rewriteFunction)
          }
        }
        route("check-resource-before-post") {
          uri(reviewServiceHost)
          path("/api/me/reviews")
          method(HttpMethod.POST)
          filters { filter(reviewRequestChecker) }
        }
        route("review-service") {
          uri(reviewServiceHost)
          path("/api/me/reviews")
          method(HttpMethod.DELETE)
        }
      }
}
