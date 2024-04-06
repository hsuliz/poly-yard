package dev.hsuliz.bookservice.dao

import dev.hsuliz.bookservice.model.Author
import dev.hsuliz.bookservice.model.Review

data class BookResponse(val title: String, val author: Author, val review: Review)
