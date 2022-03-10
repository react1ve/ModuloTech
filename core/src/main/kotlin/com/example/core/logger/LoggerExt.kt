package com.example.core.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * #Logging
 *
 * Workaround known issue: log bug report
 */
fun Logger.error(throwable: Throwable) {
    try {
        error(throwable.message, throwable)
    } catch (npe: NullPointerException) {
        error(throwable.message)
    }
}

/**
 * #Logging
 *
 * Provides lazy Logger initialization
 * Note: prefer to set @param[tag] if tag is important when code obfuscation is enabled
 */
fun Any.lazyLogger(tag: String? = null) = lazy { logger(tag) }

/**
 * #Logging
 *
 * Initialize Logger instance
 * Note: prefer to set @param[tag] if tag is important when code obfuscation is enabled
 */
fun Any.logger(tag: String? = null): Logger = LoggerFactory.getLogger(tag ?: javaClass.simpleName)
