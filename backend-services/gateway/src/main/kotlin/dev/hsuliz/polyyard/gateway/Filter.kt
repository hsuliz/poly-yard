package dev.hsuliz.polyyard.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Filter {

  @Bean
  fun routes(
      routeLocatorBuilder: RouteLocatorBuilder,
      rewriteFunction: ReviewResponseRewriteFunction
  ): RouteLocator {
    return routeLocatorBuilder
        .routes()
        .route("reviews-aggregation") { r ->
          r.path("/api/reviews")
              .filters { f ->
                f.modifyResponseBody(ByteArray::class.java, ByteArray::class.java, rewriteFunction)
              }
              .uri("http://localhost:8002")
        }
        .route("check-resource-before-post") { r ->
            //# TODO CHECK IF BOOK REALLY EXISTS
          r.path("/api/me/reviews").uri("http://localhost:8002")
        }
        .build()
  }
}
