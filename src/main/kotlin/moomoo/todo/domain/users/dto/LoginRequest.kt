package moomoo.todo.domain.users.dto

data class LoginRequest(
    val userIdentifier: String,
    val password: String
)
