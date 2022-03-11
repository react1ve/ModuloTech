package com.modulo.data.di.module

import com.modulo.data.database.DatabaseProvider
import org.koin.dsl.module

internal val databaseModule = module {
    factory { DatabaseProvider(context = get()) }
    single { get<DatabaseProvider>().deviceDao }
}
