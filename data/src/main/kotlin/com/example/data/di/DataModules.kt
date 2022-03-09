package com.example.data.di

import com.example.data.di.module.dataSourceModule
import com.example.data.di.module.databaseModule
import com.example.data.di.module.repositoryModule
import com.example.data.di.module.restModule

val dataModules by lazy {
    arrayOf(
        databaseModule,
        dataSourceModule,
        repositoryModule,
        restModule
    )
}
