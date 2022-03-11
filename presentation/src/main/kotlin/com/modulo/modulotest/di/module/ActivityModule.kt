package com.modulo.modulotest.di.module

import com.modulo.modulotest.ui.MainActivity
import com.modulo.modulotest.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val mainActivityModule = module {
    scope<MainActivity> {
        viewModel {
            MainViewModel()
        }
    }
}
