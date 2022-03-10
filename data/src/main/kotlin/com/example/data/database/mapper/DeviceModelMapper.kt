package com.example.data.database.mapper

import com.example.data.network.response.DeviceResp
import com.example.domain.model.Device
import com.example.domain.model.DeviceType

internal object DeviceModelMapper {

    fun fromNetworkToModel(resp: DeviceResp): Device {
        val type: DeviceType = fromNetworkToType(resp)
        return when (resp) {
            is DeviceResp.LightResp -> Device.Light(resp.id, resp.deviceName, type, resp.mode, resp.intensity)
            is DeviceResp.HeaterResp -> Device.Heater(
                resp.id,
                resp.deviceName,
                type,
                resp.mode,
                resp.temperature.toDouble()
            )
            is DeviceResp.RollerShutterResp -> Device.RollerShutter(resp.id, resp.deviceName, type, resp.position)
        }
    }

    private fun fromNetworkToType(device: DeviceResp): DeviceType = DeviceType.find(device.productType)
}

