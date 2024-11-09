package dev.hsuliz.polyyard.service.review.config

import dev.hsuliz.polyyard.service.review.entity.ReviewEntity
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport


@WritingConverter
class PostStatusWritingConverter : EnumWriteSupport<ReviewEntity.Category>()