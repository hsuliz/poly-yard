package dev.hsuliz.bookservice.dao

import dev.hsuliz.bookservice.model.Opinion
import java.util.*

data class AddBookRequest(val isbn: String, val dateFinished: Date, val opinion: Opinion? = null)
