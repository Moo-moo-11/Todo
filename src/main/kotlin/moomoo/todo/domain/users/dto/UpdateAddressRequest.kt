package moomoo.todo.domain.users.dto

data class UpdateAddressRequest(
    val zipcode: String,
    val streetNameAddress: String,
    val detailedAddress: String
)
