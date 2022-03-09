package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.database.DatabaseProtocol
import com.example.data.database.entity.DeviceEntity

@Dao
internal abstract class DeviceDao : BaseDao<DeviceEntity>() {

    @Query(DatabaseProtocol.DeviceTable.SELECT_ALL)
    abstract suspend fun queryAll(): List<DeviceEntity>

    @Query(DatabaseProtocol.DeviceTable.SELECT_DEVICE)
    abstract suspend fun queryDevice(deviceId: Int): DeviceEntity

    @Query(DatabaseProtocol.DeviceTable.DELETE_DEVICE)
    abstract suspend fun deleteDevice(deviceId: Int): Int
}
