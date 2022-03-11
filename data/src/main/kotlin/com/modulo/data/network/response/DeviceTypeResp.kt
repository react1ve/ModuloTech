package com.modulo.data.network.response

import com.google.gson.annotations.SerializedName

enum class DeviceTypeResp {
    @SerializedName("Light")
    LIGHT,

    @SerializedName("Heater")
    HEATER,

    @SerializedName("RollerShutter")
    ROLLER_SHUTTER;
}
