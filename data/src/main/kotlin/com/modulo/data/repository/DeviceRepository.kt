package com.modulo.data.repository

import com.modulo.data.database.DeviceDbDataSource
import com.modulo.data.database.mapper.DeviceModelMapper
import com.modulo.data.network.ApiDataSource
import com.modulo.domain.api.DeviceRepositoryApi
import com.modulo.domain.model.Device

internal class DeviceRepository(
    private val deviceDbDataSource: DeviceDbDataSource,
    private val apiDataSource: ApiDataSource
) : DeviceRepositoryApi {

    override suspend fun getDevice(deviceId: Int): Device {
        return deviceDbDataSource.getDevice(deviceId)
    }

    override suspend fun saveDevice(device: Device) {
        deviceDbDataSource.saveDevice(device)
    }

    override suspend fun deleteDevice(deviceId: Int) {
        deviceDbDataSource.deleteDevice(deviceId)
    }

    override suspend fun updateDevice(device: Device) {
        deviceDbDataSource.updateDevice(device)
    }

    override suspend fun initDevices(): List<Device> {
        return apiDataSource.getData()?.let { response ->
            response.devices.map {
                deviceDbDataSource.saveDevice(DeviceModelMapper.fromNetworkToModel(it))
            }
            getDevices()
        } ?: getDevices()
    }

    override suspend fun getDevices(): List<Device> {
        return deviceDbDataSource.getDevices()
    }
}
