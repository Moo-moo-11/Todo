package moomoo.todo.domain.users.dto

data class UpdateAddressRequest(
    val zipcode: Int?,
    val streetNameAddress: String?,
    val detailedAddress: String?
)
