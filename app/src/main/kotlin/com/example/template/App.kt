package com.example.template

import androidx.multidex.MultiDexApplication
import com.example.template.lifecycle.AppLifecycleDelegate
import com.yariksoffice.lingver.Lingver

class App : MultiDexApplication() {

    private val appLifecycleDelegate: AppLifecycleDelegate by lazy { AppLifecycleDelegate() }

    override fun onCreate() {
        super.onCreate()

        /* DI */
        initializeKoin()

        /* Multi Lang */
        initLingver()

        registerActivityLifecycleCallbacks(appLifecycleDelegate)
    }

    private fun initLingver() {
        Lingver.init(this, "eng")
    }
}

