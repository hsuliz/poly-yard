package dev.hsuliz.polyyard.service.review.util

import org.springframework.context.ApplicationEventPublisher

suspend fun <T> T.alsoPublishEvent(eventPublisher: ApplicationEventPublisher, event: Any): T {
  eventPublisher.publishEvent(event)
  return this
}
