package dev.hsuliz.bookservice.users

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findUserByUsername(username: String): User?
}
