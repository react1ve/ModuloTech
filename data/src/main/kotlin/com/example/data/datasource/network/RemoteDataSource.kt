package com.example.data.datasource.network

import com.example.data.network.NetworkDataSource
import com.example.data.network.api.DataApi
import com.example.data.network.response.DataResp
import com.google.gson.Gson

internal class RemoteDataSource(
    gson: Gson,
    private val dataApi: DataApi,
) : NetworkDataSource(gson) {

    suspend fun getData(): DataResp? = request {
        return@request dataApi.loadData()
    }

}
