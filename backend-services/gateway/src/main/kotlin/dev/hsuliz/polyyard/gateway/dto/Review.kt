package dev.hsuliz.polyyard.gateway.dto

data class Review<T>(
    val id: Long,
    val username: String,
    val type: String,
    val resource: T,
    val rating: Int,
    val comment: String?,
    val createdAt: String
)

data class Resource(val type: String, val value: String, val id: Long)
