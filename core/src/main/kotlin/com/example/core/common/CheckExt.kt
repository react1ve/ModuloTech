package com.example.core.common

fun <T> T.oneOf(vararg options: T): Boolean = options.any { it == this }
