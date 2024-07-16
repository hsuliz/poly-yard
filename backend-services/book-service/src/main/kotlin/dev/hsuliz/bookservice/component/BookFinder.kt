package dev.hsuliz.bookservice.component

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.hsuliz.bookservice.model.Book
import kotlinx.datetime.LocalDate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class BookFinder(private val bookInfoClient: WebClient) {

    suspend fun findBookByIsbn(isbn: String): Book {
        val validIsbn = getValidIsbn(isbn)
        return jacksonObjectMapper()
            .apply { registerModule(JavaTimeModule()) }
            .readValue<BookMapper>(
                bookInfoClient.get().uri("/$validIsbn").retrieve().awaitBody<String>()
            )
            .bookInfo
            .toBookModel()
    }

    private suspend fun getValidIsbn(isbnToValidate: String): String {
        when {
            isbnToValidate.matches(Regex("\\b(?:\\d{9}(\\d|X)|\\d{13})\\b")) ->
                return isbnToValidate

            else -> throw Exception("Wrong isbn") // todo create exception
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
private data class BookMapper(@JsonProperty("book") val bookInfo: BookInfo) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class BookInfo(
        val isbn13: String,
        val title: String,
        val author: List<String>,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val publishedDate: LocalDate,
        val numberOfPages: Int,
        var image: String
    ) {
        init {
            image = image.replace(Regex("._SL[0-9]+_"), "")
        }

        fun toBookModel(): Book {
            return Book(isbn13, title, author, publishedDate, numberOfPages, image)
        }
    }
}
