package dev.hsuliz.polyyard.service.review.service

import dev.hsuliz.polyyard.service.review.model.ReviewType
import dev.hsuliz.polyyard.service.review.repository.TypeRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class TypeService(private val repository: TypeRepository) {

  fun findTypesBy(ids: Flow<Long>): Flow<ReviewType> {
    return repository.findAllById(ids)
  }
}
