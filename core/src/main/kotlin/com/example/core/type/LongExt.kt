package com.example.core.type

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * #Number #Transformation
 *
 * @return Boolean value: true - if non zero Long, 0 - if zero
 */
fun Long.toBoolean(): Boolean = this != 0L

/**
 * #Number
 *
 * Power operator for integers
 */
infix fun Long.pow(exp: Int): Long = toFloat().pow(exp).roundToLong()

/**
 * #Number
 *
 * Infix way of writing kotlin.math.max
 */
infix fun Long.max(other: Long): Long = max(this, other)

/**
 * #Number
 *
 * Infix way of writing kotlin.math.min
 */
infix fun Long.min(other: Long): Long = min(this, other)
