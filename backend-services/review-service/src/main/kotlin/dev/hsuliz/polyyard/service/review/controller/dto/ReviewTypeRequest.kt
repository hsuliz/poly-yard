package dev.hsuliz.polyyard.service.review.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ReviewTypeRequest(@JsonProperty("id") val externalId: Long, val name: String)
