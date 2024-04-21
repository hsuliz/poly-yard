package dev.hsuliz.bookservice.exception

open class BookException(message: String) : Exception(message)

class BookNotFoundException(message: String) : BookException(message)

class BookAlreadyExistsException(message: String) : BookException(message)
