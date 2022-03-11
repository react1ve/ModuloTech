package com.modulo.domain.common

import java.io.IOException

sealed class Exception(cause: Throwable? = null) : RuntimeException(cause) {
    override val message: String? = detailedMessage
}

class ConnectionException : IOException()
class EmptyResponseException : IOException()
class UnauthorizedException : IOException()

data class ServerException(
    val code: Int,
    override val message: String?
) : Exception()

val Throwable.detailedMessage: String
    get() {
        val stackTrace = stackTrace.firstOrNull()
            ?.run { " at $className.$methodName($lineNumber)" }
            ?: ""
        return when (this) {
            is ServerException -> "[$code] ${message ?: ""}"
            else -> "[${javaClass.simpleName}] ${message ?: ""}"
        } + stackTrace
    }
