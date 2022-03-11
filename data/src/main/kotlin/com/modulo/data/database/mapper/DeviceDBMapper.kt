package com.modulo.data.database.mapper

import com.modulo.data.database.entity.DeviceEntity
import com.modulo.domain.model.Device
import com.modulo.domain.model.DeviceType

internal object DeviceDBMapper {

    fun fromModelToEntity(model: Device): DeviceEntity {
        val type: DeviceType = fromModelToType(model)
        val universalModel = DeviceEntity(
            id = model.id,
            deviceName = model.deviceName,
            productType = type.title
        )
        return when (model) {
            is Device.Light -> universalModel.copy(mode = model.mode, intensity = model.intensity)
            is Device.Heater -> universalModel.copy(mode = model.mode, temperature = model.temperature)
            is Device.RollerShutter -> universalModel.copy(position = model.position)
        }
    }

    fun fromEntityToModel(entity: DeviceEntity): Device {
        return when (val type: DeviceType = fromEntityToType(entity)) {
            DeviceType.LIGHT -> Device.Light(
                id = entity.id,
                deviceName = entity.deviceName,
                type,
                mode = entity.mode,
                intensity = entity.intensity
            )
            DeviceType.HEATER -> Device.Heater(
                id = entity.id,
                deviceName = entity.deviceName,
                type,
                mode = entity.mode,
                temperature = entity.temperature
            )
            DeviceType.ROLLER_SHUTTER -> Device.RollerShutter(
                id = entity.id,
                deviceName = entity.deviceName,
                type,
                position = entity.position,
            )
        }
    }

    private fun fromModelToType(model: Device): DeviceType = when (model) {
        is Device.Light -> DeviceType.LIGHT
        is Device.Heater -> DeviceType.HEATER
        is Device.RollerShutter -> DeviceType.ROLLER_SHUTTER
    }

    private fun fromEntityToType(entity: DeviceEntity): DeviceType = DeviceType.find(entity.productType)
}

