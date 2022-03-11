package com.modulo.data.network

import com.google.gson.Gson
import com.modulo.data.network.api.DataApi
import com.modulo.data.network.response.DataResp

internal open class ApiDataSource(gson: Gson, private val dataApi: DataApi) : NetworkDataSource(gson) {

    suspend fun getData(): DataResp? = request {
        return@request dataApi.loadData()
    }

}
