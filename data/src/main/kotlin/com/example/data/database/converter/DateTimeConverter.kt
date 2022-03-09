package com.example.data.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
 * Room database converter for DateTime & LocalDate objects
 */
internal object DateTimeConverter {

    @TypeConverter
    @JvmStatic
    fun dateToString(date: ZonedDateTime?): String? = date?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToDate(date: String?): ZonedDateTime? = date?.let { ZonedDateTime.parse(it) }

    @TypeConverter
    @JvmStatic
    fun localDateToString(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToLocalDate(date: String?): LocalDate? = date?.let { LocalDate.parse(it) }

    @TypeConverter
    @JvmStatic
    fun localDateTimeToString(date: LocalDateTime?): String? = date?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToLocalDateTime(dateTime: String?): LocalDateTime? =
        dateTime?.let { LocalDateTime.parse(it) }

}
