package com.modulo.data.network.response

import com.google.gson.annotations.SerializedName

internal sealed class DeviceResp(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("deviceName")
    val deviceName: String = "",
    @SerializedName("productType")
    val productType: String = ""
) {

    class LightResp(
        @SerializedName("mode")
        val mode: String,
        @SerializedName("intensity")
        val intensity: Int
    ) : DeviceResp()


    class HeaterResp(
        @SerializedName("mode")
        val mode: String,
        @SerializedName("temperature")
        val temperature: Number
    ) : DeviceResp()

    class RollerShutterResp(
        @SerializedName("position")
        val position: Int
    ) : DeviceResp()


}

