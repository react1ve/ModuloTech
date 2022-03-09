package com.example.data.network.response

import com.google.gson.annotations.SerializedName


internal data class DataResp(
    @SerializedName("devices")
    val devices: List<DeviceResp>,
    @SerializedName("user")
    val user: UserResp
)
