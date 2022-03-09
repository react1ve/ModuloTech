package com.example.domain.model

import java.io.Serializable

data class Address(
    val city: String,
    val postalCode: Int,
    val street: String,
    val country: String
) : Serializable {
    override fun equals(other: Any?): Boolean = other is Address
        && country == other.country
        && city == other.city
        && street == other.street
        && postalCode == other.postalCode

}
