package com.example.data.datasource.database

import com.example.data.database.dao.DeviceDao
import com.example.data.database.mapper.DeviceDBMapper
import com.example.data.database.mapper.DeviceModelMapper
import com.example.data.database.mapper.UserDtoMapper
import com.example.data.datasource.network.RemoteDataSource
import com.example.data.network.response.DeviceResp
import com.example.data.network.response.UserResp
import com.example.data.preferences.SharedPreferencesManager
import com.example.domain.model.Device
import com.example.domain.model.User

internal class DataSource(
    private val deviceDao: DeviceDao,
    private val remoteDataSource: RemoteDataSource,
    private val preferencesManager: SharedPreferencesManager
) {

    suspend fun getDevice(deviceId: Int): Device =
        deviceDao.queryDevice(deviceId)
            .let { DeviceDBMapper.fromEntityToModel(it) }

    suspend fun saveDevice(device: Device) =
        deviceDao.insert(DeviceDBMapper.fromModelToEntity(device))

    suspend fun deleteDevice(deviceId: Int) =
        deviceDao.deleteDevice(deviceId)

    suspend fun updateDevice(device: Device) =
        deviceDao.insert(DeviceDBMapper.fromModelToEntity(device))

    suspend fun initDevices(): List<Device> = remoteDataSource.getData()?.let { response ->
        response.devices.saveDevices()
        response.user.saveUser()
        getDevices()
    } ?: getDevices()

    suspend fun getDevices(): List<Device> = deviceDao.queryAll()
        .map { DeviceDBMapper.fromEntityToModel(it) }

    fun getUser(): User? = preferencesManager.user

    fun saveUser(user: User) {
        preferencesManager.user = user
    }

    private suspend fun List<DeviceResp>.saveDevices() {
        this.map {
            deviceDao.insert(DeviceDBMapper.fromModelToEntity(DeviceModelMapper.fromNetworkToModel(it)))
        }
    }

    private fun UserResp.saveUser() {
        saveUser(UserDtoMapper.fromDto(this))
    }

    suspend fun initUser(): User? = remoteDataSource.getData()?.let { response ->
        response.devices.saveDevices()
        response.user.saveUser()
        getUser()
    } ?: getUser()

}
