package com.example.template.ui.pages.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.example.domain.model.User
import com.example.presentation.databinding.FragmentProfileBinding
import com.example.template.common.BaseFragment
import com.example.template.common.arch.viewbinding.LoadingView
import com.example.template.ui.MainActivity
import com.example.template.ui.pages.profile.bottomsheet.SetLanguageBottomSheet
import com.example.template.ui.pages.profile.bottomsheet.SetThemeBottomSheet
import com.yariksoffice.lingver.Lingver
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
            signOut.setOnClickListener {
                viewModel.logOut()
                requireActivity().finish()
            }
            languageSetting.setOnClickListener { openLanguageBottomSheet() }
            darkModeSetting.setOnClickListener { openThemeBottomSheet() }
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

    private fun openThemeBottomSheet() {
        val bsh = SetThemeBottomSheet {
            viewModel.setAppTheme(it)
            AppCompatDelegate.setDefaultNightMode(it)
        }
        if (!bsh.isAdded) bsh.show(parentFragmentManager, "")
    }

    private fun openLanguageBottomSheet() {
        val bsh = SetLanguageBottomSheet {
            if (it == resources.configuration.locale.language) return@SetLanguageBottomSheet
            viewModel.setAppLang(it)
            Lingver.getInstance().setLocale(requireContext(), it)

            val i = Intent(requireActivity(), MainActivity::class.java)
            startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
            requireActivity().overridePendingTransition(0, 0)
        }
        if (!bsh.isAdded) bsh.show(parentFragmentManager, "")
    }

    override fun onBackPressed(): Boolean {
        navigator.back()
        return true
    }

}
