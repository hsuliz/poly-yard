package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.Resource
import dev.hsuliz.polyyard.service.review.Review
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport

@WritingConverter class CategoryWritingConverter : EnumWriteSupport<Review.Type>()

@WritingConverter class ResourceWritingConverter : EnumWriteSupport<Resource.Type>()
