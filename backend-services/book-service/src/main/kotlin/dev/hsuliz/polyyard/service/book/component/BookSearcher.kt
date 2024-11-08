package dev.hsuliz.polyyard.service.book.component

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.hsuliz.polyyard.service.book.Book
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class BookSearcher(private val bookInfoClient: WebClient) {

  suspend fun findBookByIsbn(isbn: String): Book {
    val validIsbn = getValidIsbn(isbn)
    return try {
      jacksonObjectMapper()
          .readValue<BookMapper>(
              bookInfoClient.get().uri("/?q=isbn:$validIsbn").retrieve().awaitBody<String>())
          .bookItems[0]
          .bookInfo
          .toBookModel()
    } catch (e: Exception) {
      error("Failed to map the response to BookMapper for isbn: $isbn")
    }
  }

  private suspend fun getValidIsbn(isbnToValidate: String): String {
    when {
      isbnToValidate.matches(Regex("\\b(?:\\d{9}(\\d|X)|\\d{13})\\b")) -> return isbnToValidate
      else -> throw Exception("Wrong isbn") // todo create exception
    }
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
private data class BookMapper(@JsonProperty("items") val bookItems: List<BookItem>) {
  @JsonIgnoreProperties(ignoreUnknown = true)
  data class BookItem(@JsonProperty("volumeInfo") val bookInfo: BookInfo) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class BookInfo(
        val industryIdentifiers: List<IndustryIdentifiers>? = null, // Nullable with default value
        val title: String? = null, // Nullable
        val authors: List<String>? = null, // Nullable
        val publishedDate: String? = null, // Nullable
        @JsonProperty("pageCount") val pages: Int? = null, // Nullable
        @JsonProperty("imageLinks") var image: ImageLink? = null, // Nullable
    ) {
      data class IndustryIdentifiers(val identifier: String? = null, val type: String? = null)

      @JsonIgnoreProperties(ignoreUnknown = true)
      data class ImageLink(val thumbnail: String? = null) // Nullable

      fun toBookModel(): Book {
        return Book(
            industryIdentifiers?.find { it.type == "ISBN_13" }?.identifier
                ?: "Unknown ISBN", // Fallback for null
            title ?: "Unknown Title", // Fallback for null
            authors?.getOrNull(0) ?: "Unknown Author", // Fallback for null and empty list
            publishedDate?.take(4)?.toIntOrNull() ?: 0, // Fallback for null or invalid date
            pages ?: 0, // Fallback for null
            image?.thumbnail ?: "No Image Available" // Fallback for null
            )
      }
    }
  }
}
