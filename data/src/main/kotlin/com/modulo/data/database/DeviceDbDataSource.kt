package com.modulo.data.database

import com.modulo.data.database.dao.DeviceDao
import com.modulo.data.database.mapper.DeviceDBMapper
import com.modulo.domain.model.Device

internal open class DeviceDbDataSource(private val deviceDao: DeviceDao) {

    suspend fun getDevice(deviceId: Int): Device {
        return deviceDao.queryDevice(deviceId)
            .let { DeviceDBMapper.fromEntityToModel(it) }
    }

    suspend fun saveDevice(device: Device) {
        deviceDao.insert(DeviceDBMapper.fromModelToEntity(device))
    }

    suspend fun deleteDevice(deviceId: Int) {
        deviceDao.deleteDevice(deviceId)
    }

    suspend fun updateDevice(device: Device) {
        deviceDao.insert(DeviceDBMapper.fromModelToEntity(device))
    }

    suspend fun getDevices(): List<Device> {
        return deviceDao.queryAll().map { DeviceDBMapper.fromEntityToModel(it) }
    }
}
