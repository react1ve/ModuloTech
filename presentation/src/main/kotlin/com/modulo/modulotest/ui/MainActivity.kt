package com.modulo.modulotest.ui

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.modulo.modulotest.common.BaseActivity
import com.modulo.modulotest.common.android.ext.android.applyNoStatusBarLight
import com.modulo.presentation.R
import com.modulo.presentation.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val loggerTag: String get() = this::class.java.simpleName

    override val viewModel: MainViewModel by viewModel()

    override val navigationController: NavController get() = findNavController(R.id.mainRootContainer)

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun applyTheme() {
        super.applyTheme()
        window.applyNoStatusBarLight(false)
    }

    override fun onBackPressed() {
        navigationController.navigateUp()
    }

    override fun observeLiveData() {
    }
}
