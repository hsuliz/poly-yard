package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.model.Review
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport

@WritingConverter class ReviewTypeWritingConverter : EnumWriteSupport<Review.Type>()

@WritingConverter
class ReviewResourceTypeWritingConverter : EnumWriteSupport<Review.Resource.Type>()

@ReadingConverter class ReviewTypeReadingConverter : EnumWriteSupport<Review.Type>()

@ReadingConverter
class ReviewResourceTypeReadingConverter : EnumWriteSupport<Review.Resource.Type>()
