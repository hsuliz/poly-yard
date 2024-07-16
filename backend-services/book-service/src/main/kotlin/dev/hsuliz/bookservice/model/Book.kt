package dev.hsuliz.bookservice.model

import kotlinx.datetime.LocalDate
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Books")
data class Book(
    val isbn: String,
    val title: String,
    val author: List<String> = emptyList(),
    val publishedDate: LocalDate,
    val numberOfPages: Int,
    val image: String
) {
    @Id var id = ObjectId()
}
