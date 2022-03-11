package com.modulo.data.di

import com.modulo.data.di.module.dataSourceModule
import com.modulo.data.di.module.databaseModule
import com.modulo.data.di.module.repositoryModule
import com.modulo.data.di.module.restModule

val dataModules by lazy {
    arrayOf(
        databaseModule,
        dataSourceModule,
        repositoryModule,
        restModule
    )
}
