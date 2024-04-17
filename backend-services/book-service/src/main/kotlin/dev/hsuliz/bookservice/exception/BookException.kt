package dev.hsuliz.bookservice.exception

open class BookException(message: String) : RuntimeException(message)

class BookNotFoundException(id: String) : BookException("$id not found.")
