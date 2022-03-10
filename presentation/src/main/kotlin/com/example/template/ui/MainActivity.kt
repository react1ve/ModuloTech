package com.example.template.ui

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.template.common.BaseActivity
import com.example.template.common.android.ContextWrapper
import com.example.template.common.android.ext.android.applyNoStatusBarLight
import com.example.template.common.navigation.NavigationProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavigationProvider {

    override val loggerTag: String get() = "TemplateMainActivity"

    override val viewModel: MainViewModel by viewModel()

    override val navigationController: NavController get() = findNavController(R.id.mainRootContainer)

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override val containerId: Int get() = binding.mainRootContainer.id
    override val context: ContextWrapper get() = contextWrapper

    override fun applyTheme() {
        super.applyTheme()
        window.applyNoStatusBarLight(false)
    }

    override fun onBackPressed() {

    }

    override fun observeLiveData() {

    }
}
