package dev.hsuliz.polyyard.service.review.converter

import dev.hsuliz.polyyard.service.review.model.Review
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToReviewTypeConverter : Converter<String, Review.Resource.Type> {

  override fun convert(source: String): Review.Resource.Type {
    return Review.Resource.Type.entries.find { it.name.equals(source, ignoreCase = true) }
        ?: throw IllegalArgumentException("Invalid type: $source")
  }
}
