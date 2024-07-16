package dev.hsuliz.bookservice.dao

import dev.hsuliz.bookservice.model.Review

data class BookResponse(val id: String, val title: String, val review: Review)
