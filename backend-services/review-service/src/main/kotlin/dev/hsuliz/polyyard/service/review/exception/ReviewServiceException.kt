package dev.hsuliz.polyyard.service.review.exception

open class ReviewServiceException(override val message: String?, override val cause: Throwable?) :
    Exception()

class ReviewAlreadyExistsException(override val message: String = "Review already exists!") :
    ReviewServiceException(message, null)
