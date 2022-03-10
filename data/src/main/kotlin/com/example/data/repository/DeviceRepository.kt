package com.example.data.repository

import com.example.data.database.dao.DeviceDao
import com.example.data.database.mapper.DeviceDBMapper
import com.example.data.database.mapper.DeviceModelMapper
import com.example.data.network.api.DataApi
import com.example.domain.api.DeviceRepositoryApi
import com.example.domain.model.Device
import com.google.gson.Gson

internal class DeviceRepository(
    private val deviceDao: DeviceDao,
    gson: Gson,
    api: DataApi
) : BaseRepository(gson, api), DeviceRepositoryApi {

    override suspend fun getDevice(deviceId: Int): Device {
        return deviceDao.queryDevice(deviceId)
            .let { DeviceDBMapper.fromEntityToModel(it) }
    }

    override suspend fun saveDevice(device: Device) {
        deviceDao.insert(DeviceDBMapper.fromModelToEntity(device))
    }

    override suspend fun deleteDevice(deviceId: Int) {
        deviceDao.deleteDevice(deviceId)
    }

    override suspend fun updateDevice(device: Device) {
        deviceDao.insert(DeviceDBMapper.fromModelToEntity(device))
    }

    override suspend fun initDevices(): List<Device> {
        return getData()?.let { response ->
            response.devices.map {
                deviceDao.insert(DeviceDBMapper.fromModelToEntity(DeviceModelMapper.fromNetworkToModel(it)))
            }
            getDevices()
        } ?: getDevices()
    }

    override suspend fun getDevices(): List<Device> {
        return deviceDao.queryAll()
            .map { DeviceDBMapper.fromEntityToModel(it) }
    }
}
