package com.example.core.common

val noPunctuationRegex: Regex get() = "[^a-zA-Z0-9]".toRegex()
val notNumberRegex: Regex get() = "[^0-9]".toRegex()
val hexColorRegex: Regex get() = "#([0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})".toRegex()
val nameRegex: Regex get() = "[^\\p{So}@#\\p{Z}]+$".toRegex()
val passwordRegex: Regex get() = "^[\\p{L}\\p{N}\\p{P}\\p{Z}]+$".toRegex()
