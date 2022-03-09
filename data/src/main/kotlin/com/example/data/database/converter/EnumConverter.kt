package com.example.data.database.converter

/**
 * Room database converter for enums
 */
internal object EnumConverter {
    private const val SEPARATOR = ","

    private inline fun <reified T : Enum<T>> T?.convertToString(): String? = this?.name

    private inline fun <reified T : Enum<T>> String?.convertToEnum(): T? =
        this?.let { enumValueOf<T>(it) }

    private inline fun <reified T : Enum<T>> MutableList<T>?.convertToString(): String? =
        this?.joinToString(separator = SEPARATOR) { it.name }

    private inline fun <reified T : Enum<T>> String?.convertToEnumList(): MutableList<T>? =
        this?.let { string ->
            if (string.isEmpty()) return mutableListOf()
            return string.split(SEPARATOR).map { enumValueOf<T>(it) }.toMutableList()
        }
}
