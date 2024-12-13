package dev.hsuliz.polyyard.gateway.config

import dev.hsuliz.polyyard.gateway.filter.ReviewRequestChecker
import dev.hsuliz.polyyard.gateway.filter.ReviewResponseRewriteFunction
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod

@Configuration
class FilterConfig {

  @Bean
  fun routes(
      routeLocatorBuilder: RouteLocatorBuilder,
      rewriteFunction: ReviewResponseRewriteFunction,
      reviewRequestChecker: ReviewRequestChecker
  ): RouteLocator =
      routeLocatorBuilder.routes {
        route("reviews-aggregation") {
          path("/api/reviews/**")
          uri("http://localhost:8002")
          filters {
            modifyResponseBody(ByteArray::class.java, ByteArray::class.java, rewriteFunction)
          }
        }
        route("check-resource-before-post") {
          path("/api/me/reviews")
          method(HttpMethod.POST)
          uri("http://localhost:8002")
          filters { filter(reviewRequestChecker) }
        }
        route("review-service") {
          path("/api/me/reviews")
          method(HttpMethod.DELETE)
          uri("http://localhost:8002")
        }
      }
}
