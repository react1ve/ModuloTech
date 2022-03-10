package com.example.data.di.module

import com.example.data.preferences.SharedPreferencesManager
import com.example.data.repository.BaseRepository
import org.koin.dsl.module

internal val dataSourceModule = module {
    single { SharedPreferencesManager(preferences = get(), gson = get()) }
    single {
        BaseRepository(
            gson = get(),
            dataApi = get()
        )
    }
}
