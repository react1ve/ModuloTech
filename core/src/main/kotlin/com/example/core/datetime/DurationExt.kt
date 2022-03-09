package com.example.core.datetime

import java.time.Duration
import java.time.temporal.Temporal

fun hoursBetween(startInclusive: Temporal, endExclusive: Temporal): Long = Duration
    .between(startInclusive, endExclusive)
    .toHours()

fun minutesBetween(startInclusive: Temporal, endExclusive: Temporal): Long = Duration
    .between(startInclusive, endExclusive)
    .toMinutes()

fun secondsBetween(startInclusive: Temporal, endExclusive: Temporal): Long = Duration
    .between(startInclusive, endExclusive)
    .seconds

fun Duration.toWeeks(): Long = this.toDays() / 7L
