package com.modulo.data.repository

import com.google.gson.Gson
import com.modulo.data.network.NetworkDataSource
import com.modulo.data.network.api.DataApi
import com.modulo.data.network.response.DataResp

internal open class BaseRepository(gson: Gson, private val dataApi: DataApi) : NetworkDataSource(gson) {

    suspend fun getData(): DataResp? = request {
        return@request dataApi.loadData()
    }

}
