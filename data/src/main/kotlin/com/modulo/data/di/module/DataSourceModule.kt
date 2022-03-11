package com.modulo.data.di.module

import com.modulo.data.preferences.SharedPreferencesManager
import com.modulo.data.repository.BaseRepository
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
