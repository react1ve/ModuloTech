package com.modulo.modulotest.di

import com.modulo.modulotest.di.module.deviceDetailsFragmentModule
import com.modulo.modulotest.di.module.deviceListFragmentModule
import com.modulo.modulotest.di.module.interactorModule
import com.modulo.modulotest.di.module.mainActivityModule
import com.modulo.modulotest.di.module.profileEditFragmentModule
import com.modulo.modulotest.di.module.profileFragmentModule

val presentationModules by lazy {
    arrayOf(
        interactorModule,
        *activityModules,
        *fragmentModules
    )
}

internal val activityModules =
    arrayOf(mainActivityModule)

internal val fragmentModules = arrayOf(
    deviceListFragmentModule,
    deviceDetailsFragmentModule,
    profileFragmentModule,
    profileEditFragmentModule
)
