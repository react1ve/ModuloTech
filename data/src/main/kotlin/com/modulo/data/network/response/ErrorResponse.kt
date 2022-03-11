package com.modulo.data.network.response

import com.google.gson.annotations.SerializedName


internal data class ErrorResponse(
    @SerializedName("error_description") val errorDescription: String?,
    @SerializedName("error") val error: String?,
    @SerializedName("message") val message: String?
)
