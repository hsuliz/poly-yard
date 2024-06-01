package dev.hsuliz.bookservice.component

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Book
import kotlinx.datetime.LocalDate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class BookFinder() {

    suspend fun findBookByIsbn(isbn: String): Book {

        val webClient =
            WebClient.builder()
                .baseUrl("https://api.bookscouter.com/v4/prices/sell/9785171004279")
                .build()

        val bookJson = webClient.get().retrieve().awaitBody<String>()

        val objectMapper = jacksonObjectMapper().apply { registerModule(JavaTimeModule()) }
        val bookMap: BookMap = objectMapper.readValue(bookJson)
        val book = bookMap.book
        println(book)

        return Book("Tit", "Dude", Author("Dude"))
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
private data class BookMap(
    val book: BookApiResponse
)

@JsonIgnoreProperties(ignoreUnknown = true)
private data class BookApiResponse(
    val title: String,
    val author: List<String>,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val publishedDate: LocalDate,
    val numberOfPages: Int,
    val image: String,
    val isbn13: String,
    val isbn10: String
) {
    fun toBookModel(): Book {
        return Book("Tit", "Dude", Author("Dude"))
    }
}
