package dev.hsuliz.bookservice.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Reviews")
data class Review(val username: String, val book: Book, val opinion: Opinion? = null) {
    @Id
    var id = ObjectId()
}

data class Opinion(val rating: Int, val comment: String? = null) {
    init {
        require(rating in 0..5) { "Rating must be between 0 and 5" }
    }
}
