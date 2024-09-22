package dev.hsuliz.bookservice.user

import dev.hsuliz.bookservice.user.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

  @GetMapping
  fun listAllUsers(): Flow<UserResponse> {
    val response = userService.findAllUsers().map { UserResponse(it.name) }
    return response
  }

  @GetMapping("/{username}/books")
  suspend fun getUserBooks(@PathVariable username: String): UserResponse? {
    val response = userService.findUserBooks(username)?.let { UserResponse(it.name) }
    return response
  }
}
