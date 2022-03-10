package com.example.domain.model

import java.io.Serializable

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthDate: Long
) : Serializable {

    fun getFullName() = "$firstName $lastName"
}
