package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.converter.DateTimeConverter
import com.example.data.database.converter.ListConverter
import com.example.data.database.dao.DeviceDao
import com.example.data.database.entity.DeviceEntity

@Database(
    version = 1,
    entities = [
        DeviceEntity::class
    ]
)
@TypeConverters(
    DateTimeConverter::class,
    ListConverter::class
)
internal abstract class Database : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
}
