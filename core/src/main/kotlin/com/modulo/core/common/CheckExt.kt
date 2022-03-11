package com.modulo.core.common

fun <T> T.oneOf(vararg options: T): Boolean = options.any { it == this }
