package com.example.data.di.module

import com.example.data.datasource.database.DataSource
import com.example.data.datasource.network.RemoteDataSource
import com.example.data.preferences.SharedPreferencesManager
import org.koin.dsl.module

internal val dataSourceModule = module {
    single { DataSource(deviceDao = get(), remoteDataSource = get(), preferencesManager = get()) }
    single { SharedPreferencesManager(preferences = get(), gson = get()) }
    single {
        RemoteDataSource(
            gson = get(),
            dataApi = get()
        )
    }
}
