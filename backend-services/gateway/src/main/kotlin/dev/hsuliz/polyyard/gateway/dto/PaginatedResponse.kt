package dev.hsuliz.polyyard.gateway.dto

data class PaginatedResponse<T>(
    val content: List<T>,
    val pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val first: Boolean,
    val last: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)


data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean,
    val sort: Sort
)

data class Sort(val empty: Boolean, val sorted: Boolean, val unsorted: Boolean)
