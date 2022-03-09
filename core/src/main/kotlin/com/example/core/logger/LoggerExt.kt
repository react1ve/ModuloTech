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
 * Log message if @param[condition] is satisfied
 */
fun Logger.debugIf(condition: Boolean, message: String) {
    if (condition) {
        debug(message)
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

/**
 * #Logging
 *
 * Finds method name of a caller
 * @return a method that calls current method
 */
fun callerMethod(): String? {
    val currentMethodName = "callerMethod"
    val stackTrace = Thread.currentThread().stackTrace.toList()
    val calledMethodIndex = stackTrace.indexOfFirst { it.methodName == currentMethodName } + 1
    val callerMethod = stackTrace
        .drop(calledMethodIndex + 1) // skip all methods until current one inclusive
        .find { !it.methodName.contains("\$default") } // skip kotlin-generated methods with default argument values
        ?: return null
    return callerMethod.className
        .replaceBeforeLast(".", "")
        .drop(1)
        .plus(".${callerMethod.methodName}()")
}
