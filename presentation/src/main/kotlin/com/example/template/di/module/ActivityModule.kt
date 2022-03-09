package com.example.template.di.module

import com.example.template.ui.MainActivity
import com.example.template.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val templateMainActivityModule = module {
    scope<MainActivity> {
        viewModel {
            MainViewModel()
        }
    }
}
