package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.DatabaseProtocol

@Entity(tableName = DatabaseProtocol.DeviceTable.TABLE_NAME)
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_NAME)
    val deviceName: String,
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_PRODUCT_TYPE)
    val productType: String,
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_MODE)
    val mode: String? = null,
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_INTENSITY)
    val intensity: Int? = null,
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_POSITION)
    val position: Int? = null,
    @ColumnInfo(name = DatabaseProtocol.DeviceTable.COLUMN_TEMPERATURE)
    val temperature: Int? = null

)
