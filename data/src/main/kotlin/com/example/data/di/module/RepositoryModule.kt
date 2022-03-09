package com.example.data.di.module

import com.example.data.repository.DeviceRepository
import com.example.data.repository.UserRepository
import com.example.domain.api.DeviceRepositoryApi
import com.example.domain.api.UserRepositoryApi
import org.koin.dsl.module

internal val repositoryModule = module {
    single<DeviceRepositoryApi> { DeviceRepository(dataSource = get()) }
    single<UserRepositoryApi> { UserRepository(dataSource = get()) }
}
