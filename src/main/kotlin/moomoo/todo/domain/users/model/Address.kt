package moomoo.todo.domain.users.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Address(
    @Column(name = "zip_code")
    var zipCode: String,

    @Column(name = "street_name_Address")
    var streetNameAddress: String,

    @Column(name = "detailed_address")
    var detailedAddress: String
)