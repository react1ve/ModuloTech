package com.example.data.network.response

import com.google.gson.annotations.SerializedName


internal data class AddressResp(
    @SerializedName("city")
    val city: String,
    @SerializedName("postalCode")
    val postalCode: Int,
    @SerializedName("street")
    val street: String,
    @SerializedName("streetCode")
    val streetCode: String,
    @SerializedName("country")
    val country: String
)
