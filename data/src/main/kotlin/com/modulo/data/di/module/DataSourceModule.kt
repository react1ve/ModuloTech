package com.modulo.data.di.module

import com.modulo.data.database.DeviceDbDataSource
import com.modulo.data.network.ApiDataSource
import org.koin.dsl.module

internal val dataSourceModule = module {
    single { ApiDataSource(gson = get(), dataApi = get()) }
    single { DeviceDbDataSource(deviceDao = get()) }
}
