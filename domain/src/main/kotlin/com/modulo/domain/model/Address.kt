package com.modulo.domain.model

import java.io.Serializable

data class Address(
    val city: String,
    val postalCode: Int,
    val street: String,
    val country: String
) : Serializable
