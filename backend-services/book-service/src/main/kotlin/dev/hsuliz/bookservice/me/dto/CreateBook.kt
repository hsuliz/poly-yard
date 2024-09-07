package dev.hsuliz.bookservice.me.dto

data class CreateBookRequest(
    val username: String,
    val isbn: String,
    val dateFinished: String,
    val opinion: String
)
