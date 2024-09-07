package dev.hsuliz.bookservice.users

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class UsersService(private val repository: UsersRepository) {

    fun findAllUsers(): Flow<User> {
        return repository.findAll()
    }

    suspend fun findUserByUsername(username: String): User? {
        return repository.findUserByUsername(username)
    }
}
