package com.example.template.common.android.errors

import androidx.annotation.StringRes
import com.example.domain.common.ConnectionException
import com.example.domain.common.ServerException
import com.example.domain.common.UnauthorizedException
import com.example.presentation.R
import java.net.HttpURLConnection

enum class ErrorType(@StringRes val messageId: Int) {
    CONNECTIVITY(R.string.error_connectivity),
    AUTHORIZATION(R.string.error_unauthorized),
    UNKNOWN(R.string.error_unknown_server);

    companion object {

        fun getType(error: Throwable): ErrorType = when (error) {
            is UnauthorizedException -> AUTHORIZATION
            is ConnectionException -> CONNECTIVITY
            is ServerException -> getHttpErrorType(error.code)
            else -> UNKNOWN
        }

        private fun getHttpErrorType(code: Int): ErrorType = when (code) {
            HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_PROXY_AUTH -> AUTHORIZATION
            HttpURLConnection.HTTP_NOT_FOUND -> CONNECTIVITY
            else -> UNKNOWN
        }
    }
}
