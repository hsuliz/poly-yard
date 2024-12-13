package dev.hsuliz.polyyard.service.review.exception

open class ReviewServiceException(override val message: String?, override val cause: Throwable?) :
    Exception()

class ReviewAlreadyExists(override val message: String?) : ReviewServiceException(message, null)
