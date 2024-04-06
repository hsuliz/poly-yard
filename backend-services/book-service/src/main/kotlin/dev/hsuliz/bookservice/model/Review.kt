package dev.hsuliz.bookservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document data class Review(@Id val id: String? = null, val rating: Int, val comment: String)
