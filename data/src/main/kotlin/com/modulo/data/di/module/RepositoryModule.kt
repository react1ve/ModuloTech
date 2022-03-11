package com.modulo.data.di.module

import com.modulo.data.repository.DeviceRepository
import com.modulo.data.repository.UserRepository
import com.modulo.domain.api.DeviceRepositoryApi
import com.modulo.domain.api.UserRepositoryApi
import org.koin.dsl.module

internal val repositoryModule = module {
    single<DeviceRepositoryApi> { DeviceRepository(deviceDbDataSource = get(), apiDataSource = get()) }
    single<UserRepositoryApi> { UserRepository(preferencesManager = get(), apiDataSource = get()) }
}
