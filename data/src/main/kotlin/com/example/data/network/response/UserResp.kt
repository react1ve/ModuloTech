package com.example.data.network.response

import com.google.gson.annotations.SerializedName


internal data class UserResp(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("address")
    val address: AddressResp,
    @SerializedName("birthDate")
    val birthDate: Long
)
