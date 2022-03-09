package com.example.template.di.module

import com.example.domain.interactor.DeviceInteractor
import com.example.domain.interactor.UserInteractor
import org.koin.dsl.module

val interactorModule = module {
    single { DeviceInteractor(deviceRepository = get()) }
    single { UserInteractor(userRepositoryApi = get()) }
}
