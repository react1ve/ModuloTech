package com.example.template

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.example.data.di.dataModules
import com.example.template.common.android.ResourceProvider
import com.example.template.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun App.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        androidLogger(Level.DEBUG)
        modules(
            this@initializeKoin.module(),
            *dataModules,
            *presentationModules
        )
    }
}

fun App.module() = org.koin.dsl.module {
    single { this@module }
    single<SharedPreferences> { getSharedPreferences(packageName, Context.MODE_PRIVATE) }
    single<ResourceProvider> { ResourceProvider(context = get()) }
    single { getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
}
