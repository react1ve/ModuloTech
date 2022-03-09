package com.example.data.network.api

import com.example.data.network.response.DataResp
import retrofit2.http.GET

internal interface DataApi {

    @GET(DATA)
    suspend fun loadData(): DataResp

    companion object {
        const val DATA = "data.json"
    }
}
