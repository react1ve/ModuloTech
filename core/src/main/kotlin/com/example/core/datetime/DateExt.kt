package com.example.core.datetime

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

fun ZonedDateTime.millis(): Long = this
    .toInstant()
    .toEpochMilli()

fun LocalDateTime.millis(offset: ZoneOffset): Long = this
    .toInstant(offset)
    .toEpochMilli()

fun ZonedDateTime.isToday() = this.toLocalDate() == LocalDate.now()

fun ZonedDateTime.isTomorrow() = this.toLocalDate() == LocalDate.now().plusDays(1)

fun ZonedDateTime.isYesterday() = this.toLocalDate() == LocalDate.now().minusDays(1)

fun ZonedDateTime.isAfterNow() = this.isAfter(ZonedDateTime.now())

fun ZonedDateTime.isBeforeNow() = this.isBefore(ZonedDateTime.now())

fun ZonedDateTime.firstDayOfMonth(): ZonedDateTime = this
    .with(TemporalAdjusters.firstDayOfMonth())
    .withTimeAtStartOfDay()

fun ZonedDateTime.lastDayOfMonth(): ZonedDateTime = this
    .with(TemporalAdjusters.lastDayOfMonth())
    .withTimeAtStartOfDay()

fun ZonedDateTime.firstDayOfWeek(locale: Locale): ZonedDateTime = this
    .with(WeekFields.of(locale).dayOfWeek(), 1)
    .withTimeAtStartOfDay()

fun ZonedDateTime.lastDayOfWeek(locale: Locale): ZonedDateTime = this
    .with(WeekFields.of(locale).dayOfWeek(), 7)
    .withTimeAtStartOfDay()

fun ZonedDateTime.withTimeAtStartOfDay(): ZonedDateTime = this
    .withHour(0)
    .withMinute(0)
    .withSecond(0)
    .withNano(0)

fun zonedDateTimeOfMillis(millis: Long, zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime = Instant
    .ofEpochMilli(millis)
    .atZone(zoneId)

fun Date.convertToDateTime(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime =
    ZonedDateTime.ofInstant(this.toInstant(), zoneId)
