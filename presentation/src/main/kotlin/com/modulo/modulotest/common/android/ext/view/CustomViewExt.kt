package com.modulo.modulotest.common.android.ext.view

import com.shahryar.airbar.AirBar

fun AirBar.onProgressChanged(listener: (progress: Double) -> Unit) {
    this.setOnProgressChangedListener(object : AirBar.OnProgressChangedListener {
        override fun afterProgressChanged(airBar: AirBar, progress: Double, percentage: Double) {
        }

        override fun onProgressChanged(airBar: AirBar, progress: Double, percentage: Double) {
            listener.invoke(progress)
        }
    })
}
