package com.example.data.di.module

import com.example.data.database.DatabaseProvider
import org.koin.dsl.module

internal val databaseModule = module {
    single { DatabaseProvider(context = get()) }
    single { get<DatabaseProvider>().deviceDao }
}
