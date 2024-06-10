package moomoo.todo.domain.users.dto

data class SignUpRequest(
    val userIdentifier: String,
    val name: String,
    val password: String,
    val zipCode: Int?,
    val streetNameAddress: String?,
    val detailedAddress: String?,
    val role: String
)
