package com.modulo.data.network.api

import com.modulo.data.network.response.DataResp
import retrofit2.http.GET

internal interface DataApi {

    @GET(DATA)
    suspend fun loadData(): DataResp

    companion object {
        const val DATA = "data.json"
    }
}
