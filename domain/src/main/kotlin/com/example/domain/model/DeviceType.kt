package com.example.domain.model

import java.io.Serializable
import java.util.*

enum class DeviceType(val title: String) : Serializable {
    LIGHT("Light"),
    HEATER("Heater"),
    ROLLER_SHUTTER("RollerShutter");

    companion object {
        fun find(key: String) = values()
            .firstOrNull { key.lowercase(Locale.getDefault()) == it.title.lowercase(Locale.getDefault()) }
            ?: LIGHT
    }
}
