package dev.hsuliz.polyyard.gateway.dto

data class Book(
    val id: Long,
    val isbn: String,
    val title: String,
    val author: String,
    val image: String,
    val pages: Int,
    val publishedDate: Int
)