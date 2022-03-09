package com.example.data.repository

import com.example.data.datasource.database.DataSource
import com.example.domain.api.DeviceRepositoryApi
import com.example.domain.model.Device

internal class DeviceRepository(private val dataSource: DataSource) : DeviceRepositoryApi {

    override suspend fun getDevice(deviceId: Int): Device = dataSource.getDevice(deviceId)

    override suspend fun saveDevice(device: Device) {
        dataSource.saveDevice(device)
    }

    override suspend fun deleteDevice(deviceId: Int) {
        dataSource.deleteDevice(deviceId)
    }

    override suspend fun updateDevice(car: Device) {
        dataSource.updateDevice(car)
    }

    override suspend fun initDevices() = dataSource.initDevices()

    override suspend fun getDevices(): List<Device> = dataSource.getDevices()
}
