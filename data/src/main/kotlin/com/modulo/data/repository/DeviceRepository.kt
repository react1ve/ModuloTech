package com.modulo.data.repository

import com.modulo.data.database.dao.DeviceDao
import com.modulo.data.database.mapper.DeviceDBMapper
import com.modulo.data.database.mapper.DeviceModelMapper
import com.modulo.data.network.ApiDataSource
import com.modulo.domain.api.DeviceRepositoryApi
import com.modulo.domain.model.Device

internal class DeviceRepository(
    private val deviceDao: DeviceDao,
    private val apiDataSource: ApiDataSource
) : DeviceRepositoryApi {

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
        return apiDataSource.getData()?.let { response ->
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
