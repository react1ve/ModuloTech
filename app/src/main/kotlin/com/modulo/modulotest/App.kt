package com.modulo.modulotest

import androidx.multidex.MultiDexApplication
import com.yariksoffice.lingver.Lingver

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        /* Multi Lang */
        initLingver()

        /* DI */
        initializeKoin()
    }

    private fun initLingver() {
        Lingver.init(this, "eng")
    }
}

