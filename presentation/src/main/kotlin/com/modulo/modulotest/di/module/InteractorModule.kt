package com.modulo.modulotest.di.module

import com.modulo.domain.interactor.DeviceInteractor
import com.modulo.domain.interactor.UserInteractor
import org.koin.dsl.module

val interactorModule = module {
    single { DeviceInteractor(deviceRepository = get()) }
    single { UserInteractor(userRepositoryApi = get()) }
}
