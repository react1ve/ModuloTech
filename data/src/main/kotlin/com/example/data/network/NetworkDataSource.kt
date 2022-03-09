package com.example.data.network

import com.example.core.common.oneOf
import com.example.data.network.NetworkDataSource.ErrorParser
import com.example.data.network.response.ErrorResponse
import com.example.domain.common.ConnectionException
import com.example.domain.common.ServerException
import com.example.domain.common.UnauthorizedException
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

internal open class NetworkDataSource(
    protected val gson: Gson
) {

    protected val defaultErrorParser = ErrorParser { _, code, responseBody ->
        val errorBody = responseBody?.let {
            try {
                gson.fromJson(responseBody, ErrorResponse::class.java)
            } catch (e: IOException) {
                null
            }
        }
        if (code.oneOf(HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_PROXY_AUTH))
            UnauthorizedException()
        else ServerException(
            code,
            errorBody?.let { it.message ?: it.error ?: it.errorDescription }
        )
    }

    /**
     * Retrofit can't handle no-content response by default, so such requests should return Response<Unit>
     * and be called with this method that will execute them and throw HttpException in case of error
     */
    suspend fun requestUnit(
        parser: ErrorParser = defaultErrorParser,
        call: suspend () -> Response<*>
    ) = request(parser) {
        val response = call()
        if (!response.isSuccessful) throw HttpException(response)
    }

    /**
     * Retrofit can't handle no-content (204) response by default, so such requests should return Response<T>
     * and be called with this method that will execute them, return [default] value in case of empty response
     * and throw HttpException in case of error
     */
    suspend fun <T> requestDefaultIfEmpty(
        default: T,
        parser: ErrorParser = defaultErrorParser,
        call: suspend () -> Response<T>
    ) = request(parser) {
        val response = call()
        if (!response.isSuccessful) throw HttpException(response)
        response.body() ?: default
    }

    /**
     * Wraps suspend call in a Flow and calls processError on it
     */
    suspend fun <T> request(
        parser: ErrorParser = defaultErrorParser,
        call: suspend () -> T
    ): T? {
        try {
            return call.invoke()
        } catch (t: Throwable) {
            throw parseError(parser, t)
        }
    }

    private fun parseError(parser: ErrorParser, throwable: Throwable): Throwable = when (throwable) {
        is TimeoutException, is ConnectException, is SocketTimeoutException, is UnknownHostException ->
            ConnectionException()
        is HttpException -> {
            parser.parseHttpError(throwable, throwable.code(), throwable.response()?.errorBody()?.toString())
                ?: throwable
        }
        else -> throwable
    }

    fun interface ErrorParser {
        fun parseHttpError(error: HttpException, code: Int, responseBody: String?): Throwable?
    }
}
