package dev.hsuliz.bookservice.users

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UsersRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findUserByUsername(username: String): User?
}
