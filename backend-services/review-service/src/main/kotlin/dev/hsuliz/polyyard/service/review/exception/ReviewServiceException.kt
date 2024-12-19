package dev.hsuliz.polyyard.service.review.exception

open class ReviewServiceException(override val message: String?, override val cause: Throwable?) :
    Exception(message)

class ReviewAlreadyExistsException : ReviewServiceException("Review already exists!", null)

class ReviewResourceNotFoundException : ReviewServiceException("Review already exists!", null)
