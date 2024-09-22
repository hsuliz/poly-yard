package dev.hsuliz.bookservice.book.component

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.hsuliz.bookservice.book.model.Book
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
      error("Failed to map the response to BookMapper")
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
        val industryIdentifiers: List<IndustryIdentifiers>,
        val title: String,
        val authors: List<String>,
        val publishedDate: String,
        @JsonProperty("pageCount") val pages: Int,
        @JsonProperty("imageLinks") var image: ImageLink,
    ) {
      data class IndustryIdentifiers(val identifier: String, val type: String)

      @JsonIgnoreProperties(ignoreUnknown = true) data class ImageLink(val thumbnail: String)

      fun toBookModel(): Book {
        return Book(
            industryIdentifiers.find { it.type == "ISBN_13" }!!.identifier,
            title,
            authors[0],
            publishedDate.take(4).toInt(),
            pages,
            image.thumbnail,
        )
      }
    }
  }
}
