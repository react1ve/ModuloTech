package com.example.data.di.module

import com.example.data.database.DatabaseProvider
import org.koin.dsl.module

internal val databaseModule = module {
    factory { DatabaseProvider(context = get()) }
    single { get<DatabaseProvider>().deviceDao }
}
