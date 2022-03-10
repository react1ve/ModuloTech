package com.example.template

import androidx.multidex.MultiDexApplication
import com.yariksoffice.lingver.Lingver

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        /* DI */
        initializeKoin()

        /* Multi Lang */
        initLingver()
    }

    private fun initLingver() {
        Lingver.init(this, "eng")
    }
}

