package com.example.core.common

/**
 * #Convenience
 *
 * Non-infix alias to `as` keyword to allow including it in chain without unnecessary parenthesis
 */
inline fun <reified T> Any.cast(): T = this as T

/**
 * #Convenience
 *
 * Non-infix alias to `as?` keyword to allow including it in chain without unnecessary parenthesis
 */
inline fun <reified T> Any.safeCast(): T? = this as? T

/**
 * #ControlFlow #Convenience
 *
 * Non-infix alternative for ?: to chain operators without unnecessary parenthesis
 */
fun <T> T?.orElse(other: T): T = this ?: other

/**
 * #ControlFlow #Convenience
 *
 * Non-infix alternative for ?: to chain operators without unnecessary parenthesis. Calculation inside
 * brackets not executed when [this] is not null, like with regular ?:
 */
inline fun <T> T?.orElse(producer: () -> T): T = this ?: producer()

/**
 * #ControlFlow #Convenience
 *
 * Infix method to execute [action] when current value returned null.
 *
 * Can be used to mix data control ( something?.let {...} ) and
 * flow control (if (something != null) {...}) styles
 */
inline infix fun <T> T?.otherwise(action: () -> Unit) {
    if (this == null) action()
}
