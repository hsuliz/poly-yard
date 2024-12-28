package dev.hsuliz.polyyard.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BookServiceRoutesConfig(@Value("\${book-service-host}") private val bookServiceHost: String) {
  @Bean
  fun bookRoutes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
      routeLocatorBuilder.routes {
        route("book-service") {
          path("/api/books/**")
          uri(bookServiceHost)
        }
      }
}
