package dev.hsuliz.bookservice.users

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("Users")
data class User(@Id var id: UUID? = null, @Column("username") val username: String)

// @Table("Books")
// data class Book(
//    val isbn: String,
//    val title: String,
//    val author: String,
//    val publishedDate: String,
//    val numberOfPages: String,
//    val image: String,
//    @Id var id: Long? = null,
// ) {}
