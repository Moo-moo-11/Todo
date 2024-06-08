package moomoo.todo.domain.users.model

import jakarta.persistence.*
import moomoo.todo.domain.users.dto.UserResponse

@Entity (name = "app_user")
class User(
    @Column(name = "user_identifier", nullable = false, unique = true)
    val userIdentifier: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Embedded
    var address: Address,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.USER
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateAddress(zipcode: String?, streetNameAddress: String?, detailedAddress: String?) {
        address = Address(zipcode, streetNameAddress, detailedAddress)
    }
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        userIdentifier = userIdentifier,
        name = name,
        zipCode = address.zipCode,
        streetNameAddress = address.streetNameAddress,
        detailedAddress = address.detailedAddress,
        role = role.name
    )
}