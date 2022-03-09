package com.example.domain.model

import java.io.Serializable

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthDate: Long
) : Serializable {
    override fun equals(other: Any?): Boolean = other is User
        && firstName == other.firstName
        && lastName == other.lastName
        && address == other.address
        && birthDate == other.birthDate

    fun getFullName() = "$firstName $lastName"
}
