package com.example.core.common

/**
 * #Convenience
 *
 * Non-infix alias to `as?` keyword to allow including it in chain without unnecessary parenthesis
 */
inline fun <reified T> Any.safeCast(): T? = this as? T
