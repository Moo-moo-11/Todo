package moomoo.todo.domain.users.dto

data class UserResponse(
    val id: Long,
    val userIdentifier: String,
    val name: String,
    val zipCode: String?,
    val streetNameAddress: String?,
    val detailedAddress: String?,
    val role: String
)
