package dev.hsuliz.polyyard.gateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ReviewResponseRewriteFunction(private val bookWebClient: WebClient) :
    RewriteFunction<ByteArray, ByteArray> {

  override fun apply(exchange: ServerWebExchange, body: ByteArray): Mono<ByteArray> {
    val objectMapper = jacksonObjectMapper()

    val paginatedResponse: PaginatedResponse<Review> = objectMapper.readValue(body)

    val reviews = paginatedResponse.content

    val isbnList = reviews.filter { it.resource.type == "ISBN" }.map { it.resource.value }

    return fetchBooksByIsbn(isbnList).map { books ->
      val enrichedReviews =
          reviews.map { review ->
            if (review.resource.type == "ISBN") {
              val book = books.find { it.isbn == review.resource.value }
              if (book != null) review.copy(test = book) else review
            } else review
          }

      val enrichedResponse = paginatedResponse.copy(content = enrichedReviews)
      objectMapper.writeValueAsBytes(enrichedResponse)
    }
  }

  private fun fetchBooksByIsbn(isbnList: List<String>): Mono<List<Book>> {
    return bookWebClient
        .get()
        .uri { uriBuilder ->
          uriBuilder.path("/api/books").queryParam("isbn", isbnList.joinToString(",")).build()
        }
        .retrieve()
        .bodyToMono(object : ParameterizedTypeReference<List<Book>>() {})
  }
}

data class Book(
    val id: Long,
    val isbn: String,
    val title: String,
    val author: String,
    val image: String,
    val pages: Int,
    val publishedDate: Int
)

data class PaginatedResponse<T>(
    val content: List<T>,
    val pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val first: Boolean,
    val last: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean,
    val sort: Sort
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Review(
    val id: Long,
    val username: String,
    val type: String,
    val resource: Resource,
    val test: Any?,
    val rating: Int,
    val comment: String?,
    val createdAt: String
)

data class Resource(val type: String, val value: String, val id: Long)
