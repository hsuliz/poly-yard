package dev.hsuliz.bookservice.user

import dev.hsuliz.bookservice.user.model.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findUserByName(name: String): User?
}
