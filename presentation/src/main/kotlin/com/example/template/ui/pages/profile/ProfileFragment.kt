package com.example.template.ui.pages.profile

import androidx.lifecycle.Observer
import com.example.domain.model.User
import com.example.presentation.databinding.FragmentProfileBinding
import com.example.template.common.BaseFragment
import com.example.template.common.arch.viewbinding.LoadingView
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(), LoadingView {

    override val loggerTag: String get() = this::class.java.simpleName
    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: ProfileNavigator by lazy { ProfileNavigator(navigationController) }

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.profileFragment.applyStatusBarInsetWithPadding()
    }

    override fun initClicks() {
        super.initClicks()
        with(binding) {
            edit.setOnClickListener { navigator.openEditProfile() }
            signOut.setOnClickListener { /*todo*/ }
            languageSetting.setOnClickListener { /*todo*/ }
            darkModeSetting.setOnClickListener { /*todo*/ }
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.userState.observe(viewLifecycleOwner, observeUserState())
        viewModel.loadingProgressEvent.observe(viewLifecycleOwner, observeLoadingProgressEvent())
        viewModel.loadingErrorEvent.observe(viewLifecycleOwner, observeLoadingErrorEvent())
    }

    private fun observeUserState(): Observer<User> = Observer { showUser(it) }
    private fun observeLoadingProgressEvent(): Observer<Boolean> = Observer { showLoadingIf(it) }
    private fun observeLoadingErrorEvent(): Observer<String?> = Observer { showError(it) }

    private fun showUser(user: User) {
        with(binding) {
            fullName.text = user.getFullName()
        }
    }
}
