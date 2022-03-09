package com.example.core.type

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * #Number #Transformation
 *
 * @return Boolean value: true - if non zero Int, 0 - if zero
 */
fun Int.toBoolean(): Boolean = this != 0

/**
 * #Number
 *
 * Power operator for integers
 */
infix fun Int.pow(exp: Int): Int = toFloat().pow(exp).roundToInt()

/**
 * #Number
 *
 * Infix way of writing kotlin.math.max
 */
infix fun Int.max(other: Int): Int = max(this, other)

/**
 * #Number
 *
 * Infix way of writing kotlin.math.min
 */
infix fun Int.min(other: Int): Int = min(this, other)

fun Int.unicodeToString() = String(Character.toChars(this))
