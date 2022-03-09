package com.example.core.type

/**
 * #Collection #Map
 *
 * Helper function that returns map values in specified order, returns list of values, can be used
 * for destructuring declarations
 *
 * val (key1value, key2value, key3value) = createMap().order(key1, key2, key3)
 */
fun <K, V> Map<K, V>.order(vararg order: K): List<V?> = order.map { get(it) }

/**
 * #Collection
 *
 * Returns this if it's not empty, or collection returned by [producer] otherwise
 */
fun <T, Col : Collection<T>> Col.replaceEmpty(producer: () -> Col) =
    if (this.isEmpty()) producer() else this
