package com.example.data.repository

import com.example.data.network.NetworkDataSource
import com.example.data.network.api.DataApi
import com.example.data.network.response.DataResp
import com.google.gson.Gson

internal open class BaseRepository(gson: Gson, private val dataApi: DataApi) : NetworkDataSource(gson) {

    suspend fun getData(): DataResp? = request {
        return@request dataApi.loadData()
    }

}
