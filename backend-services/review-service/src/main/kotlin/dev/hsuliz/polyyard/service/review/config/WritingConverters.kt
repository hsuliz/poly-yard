package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.model.Review
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport

@WritingConverter class CategoryWritingConverter : EnumWriteSupport<Review.Type>()

@WritingConverter class ResourceWritingConverter : EnumWriteSupport<Review.Resource.Type>()
