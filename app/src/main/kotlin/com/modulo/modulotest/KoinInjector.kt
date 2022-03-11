package com.modulo.modulotest

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.modulo.data.di.dataModules
import com.modulo.modulotest.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun App.initializeKoin() {
    startKoin {
        if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
        androidContext(this@initializeKoin)
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
    single { getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
}
