package dev.hsuliz.polyyard.service.review.dto

data class ReviewRequest(
    val type: ContentType,
    val resource: Resource,
    val rating: Int,
    val comment: String?
)

enum class ContentType {
    Book,
    Album
}

data class Resource(
    val type: ResourceType,
    val id: String
)

enum class ResourceType {
    ISBN,
    ISRC,
    UPC
}
