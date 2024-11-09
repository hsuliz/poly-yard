package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.entity.Resource
import dev.hsuliz.polyyard.service.review.entity.Review
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport

@WritingConverter class CategoryWritingConverter : EnumWriteSupport<Review.Category>()
@WritingConverter class ResourceWritingConverter : EnumWriteSupport<Resource.Type>()
