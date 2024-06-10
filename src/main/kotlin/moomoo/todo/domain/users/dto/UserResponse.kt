package moomoo.todo.domain.users.dto

data class UserResponse(
    val id: Long,
    val userIdentifier: String,
    val name: String,
    val zipCode: Int?,
    val streetNameAddress: String?,
    val detailedAddress: String?,
    val role: String
)
