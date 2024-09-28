package dev.hsuliz.bookservice.user

import dev.hsuliz.bookservice.user.model.User
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    suspend fun createUser(username: String): User {
        return repository.save(User(username))
    }

    suspend fun findUser(username: String): User? {
        return repository.findUserByName(username)
    }

    fun findAllUsers(): Flow<User> {
        return repository.findAll()
    }

    suspend fun findUserBooks(username: String): User? {
        return repository.findUserByName(username)
    }
}
