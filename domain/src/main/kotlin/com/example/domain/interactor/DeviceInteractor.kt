package com.example.domain.interactor

import com.example.domain.api.DeviceRepositoryApi
import com.example.domain.model.Device

class DeviceInteractor(private val deviceRepository: DeviceRepositoryApi) {

    suspend fun getDevice(deviceId: Int): Device = deviceRepository.getDevice(deviceId)

    suspend fun saveDevice(device: Device) = deviceRepository.saveDevice(device)

    suspend fun deleteDevice(deviceId: Int) = deviceRepository.deleteDevice(deviceId)

    suspend fun updateDevice(device: Device) = deviceRepository.updateDevice(device)

    suspend fun initDevices() = deviceRepository.initDevices()

    suspend fun getDevices(): List<Device> = deviceRepository.getDevices()
}
