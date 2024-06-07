package moomoo.todo.domain.users.controller

import moomoo.todo.domain.users.dto.*
import moomoo.todo.domain.users.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
    val userService: UserService
) {
    @GetMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        TODO()
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        TODO()
    }

    @PostMapping("/singup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        TODO()
    }

    @PutMapping("/{userId}")
    fun updateAddress(@PathVariable userId: Long, @RequestBody updateAddressRequest: UpdateAddressRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateAddress(userId, updateAddressRequest))
    }

}