package com.example.data.database

internal object DatabaseProtocol {

    const val DATABASE_NAME = "ModuloTechDatabase"

    /**
     * Protocol for [DeviceEntity]
     */
    internal object DeviceTable {

        const val TABLE_NAME = "devices"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRODUCT_TYPE = "product_type"
        const val COLUMN_MODE = "mode"
        const val COLUMN_INTENSITY = "intensity"
        const val COLUMN_POSITION = "position"
        const val COLUMN_TEMPERATURE = "temperature"

        const val SELECT_ALL = "SELECT * FROM $TABLE_NAME"
        const val SELECT_DEVICE = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = :deviceId LIMIT 1"
        const val DELETE_DEVICE = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :deviceId"
    }
}
