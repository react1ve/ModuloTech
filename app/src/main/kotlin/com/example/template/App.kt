package com.example.template

import androidx.multidex.MultiDexApplication
import com.example.template.lifecycle.AppLifecycleDelegate

class App : MultiDexApplication() {

    private val appLifecycleDelegate: AppLifecycleDelegate by lazy { AppLifecycleDelegate() }

    override fun onCreate() {
        super.onCreate()

        /* DI */
        initializeKoin()

        registerActivityLifecycleCallbacks(appLifecycleDelegate)
    }
}
