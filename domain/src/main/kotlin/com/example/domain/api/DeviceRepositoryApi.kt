package com.example.domain.api

import com.example.domain.model.Device

interface DeviceRepositoryApi {

    suspend fun getDevice(deviceId: Int): Device

    suspend fun saveDevice(device: Device)

    suspend fun deleteDevice(deviceId: Int)

    suspend fun updateDevice(device: Device)

    suspend fun initDevices(): List<Device>

    suspend fun getDevices(): List<Device>
}
