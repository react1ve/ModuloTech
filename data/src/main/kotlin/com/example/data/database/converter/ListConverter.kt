package com.example.data.database.converter

import androidx.room.TypeConverter

internal object ListConverter {

    private const val SEPARATOR = ","

    @TypeConverter
    @JvmStatic
    fun intListToString(list: List<Int>): String? =
        list.joinToString(separator = SEPARATOR) { it.toString() }

    @TypeConverter
    @JvmStatic
    fun stringToIntList(string: String?): List<Int>? =
        string?.split(SEPARATOR)?.map(String::toInt)

    @TypeConverter
    @JvmStatic
    fun stringListToString(list: List<String>): String =
        list.joinToString(separator = SEPARATOR) { it }

    @TypeConverter
    @JvmStatic
    fun stringToStringList(string: String?): List<String>? =
        string?.split(SEPARATOR)
}
