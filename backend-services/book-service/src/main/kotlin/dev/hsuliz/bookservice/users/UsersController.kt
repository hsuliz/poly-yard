package dev.hsuliz.bookservice.users

import dev.hsuliz.bookservice.users.dto.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping
    fun listAllUsers(): Flow<UserResponse> {
        val response = usersService.findAllUsers().map { UserResponse(it.username) }
        return response
    }

    @GetMapping("/{username}/books")
    suspend fun getUserBooks(@PathVariable username: String): UserResponse? {
        val response = usersService.findUserBooks(username)?.let { UserResponse(it.username) }
        return response
    }
}
