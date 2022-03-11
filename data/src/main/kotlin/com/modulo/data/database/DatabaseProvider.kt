package com.modulo.data.database

import android.content.Context
import androidx.room.Room

internal class DatabaseProvider(context: Context) {
    private val database = Room
        .databaseBuilder(
            context,
            Database::class.java,
            DatabaseProtocol.DATABASE_NAME
        )
        .build()

    val deviceDao get() = database.deviceDao()
}
