package com.example.domain.model

import java.io.Serializable

sealed class Device(
    open val id: Int,
    open val deviceName: String,
    open val productType: DeviceType
) : Serializable {

    protected val on = "ON"

    data class Light(
        override val id: Int,
        override val deviceName: String,
        override val productType: DeviceType,

        val mode: String?,
        val intensity: Int?,
    ) : Device(
        id = id,
        deviceName = deviceName,
        productType = productType
    ) {
        fun isChecked() = mode == on
    }

    data class Heater(
        override val id: Int,
        override val deviceName: String,
        override val productType: DeviceType,

        val mode: String?,
        val temperature: Int?,
    ) : Device(
        id = id,
        deviceName = deviceName,
        productType = productType
    ) {
        fun isChecked() = mode == on
    }

    data class RollerShutter(
        override val id: Int,
        override val deviceName: String,
        override val productType: DeviceType,

        val position: Int?,
    ) : Device(
        id = id,
        deviceName = deviceName,
        productType = productType
    )
}
