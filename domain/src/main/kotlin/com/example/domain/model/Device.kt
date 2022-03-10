package com.example.domain.model

import java.io.Serializable

sealed class Device(
    open val id: Int,
    open val deviceName: String,
    open val productType: DeviceType
) : Serializable {

    companion object {
        protected const val on = "ON"
        protected const val off = "OFF"
    }

    data class Light(
        override val id: Int,
        override val deviceName: String,
        override val productType: DeviceType,

        var mode: String?,
        var intensity: Int?,
    ) : Device(
        id = id,
        deviceName = deviceName,
        productType = productType
    ) {
        fun isChecked() = mode == on
        fun setChecked(checked: Boolean) {
            mode = if (checked) on else off
        }
    }

    data class Heater(
        override val id: Int,
        override val deviceName: String,
        override val productType: DeviceType,

        var mode: String?,
        var temperature: Double?,
    ) : Device(
        id = id,
        deviceName = deviceName,
        productType = productType
    ) {
        fun isChecked() = mode == on
        fun setChecked(checked: Boolean) {
            mode = if (checked) on else off
        }
    }

    data class RollerShutter(
        override val id: Int,
        override val deviceName: String,
        override val productType: DeviceType,

        var position: Int?,
    ) : Device(
        id = id,
        deviceName = deviceName,
        productType = productType
    )
}
