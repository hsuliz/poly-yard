package dev.hsuliz.polyyard.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Filter {

  @Bean
  fun routes(
      routeLocatorBuilder: RouteLocatorBuilder,
      rewriteFunction: ReviewResponseRewriteFunction
  ): RouteLocator =
      routeLocatorBuilder.routes {
        route("reviews-aggregation") {
          path("/api/reviews")
          uri("http://localhost:8002")
          filters {
            modifyResponseBody(ByteArray::class.java, ByteArray::class.java, rewriteFunction)
          }
        }
        route("check-resource-before-post") {
          path("/api/me/reviews")
          uri("http://localhost:8002")
        }
      }
}
