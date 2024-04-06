package dev.hsuliz.bookservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Author(@Id val id: String? = null, val firstName: String, val secondName: String)
