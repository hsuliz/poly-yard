package dev.hsuliz.bookservice.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    val name: String,
    @Id var id: Long? = null,
)
