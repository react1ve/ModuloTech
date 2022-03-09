package com.example.template.di

import com.example.template.di.module.deviceDetailsFragmentModule
import com.example.template.di.module.deviceListFragmentModule
import com.example.template.di.module.interactorModule
import com.example.template.di.module.profileEditFragmentModule
import com.example.template.di.module.profileFragmentModule
import com.example.template.di.module.templateMainActivityModule

val presentationModules by lazy {
    arrayOf(
        interactorModule,
        *activityModules,
        *fragmentModules
    )
}

internal val activityModules =
    arrayOf(templateMainActivityModule)

internal val fragmentModules = arrayOf(
    deviceListFragmentModule,
    deviceDetailsFragmentModule,
    profileFragmentModule,
    profileEditFragmentModule
)
