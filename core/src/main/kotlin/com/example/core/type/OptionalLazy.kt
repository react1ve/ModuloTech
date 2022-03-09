package com.example.core.type

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Lazy for **var** properties.
 * Lazy that can be null later.
 *
 * initializer will be used if value is null. Even if you set it as null.
 */
fun <V> optionalLazy(initializer: () -> V) = OptionalLazy(initializer)

class OptionalLazy<V>(private val initializer: () -> V) : ReadWriteProperty<Any?, V> {

    private var value: V? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>) =
        value ?: initializer().also { value = it }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        this.value = value
    }
}
