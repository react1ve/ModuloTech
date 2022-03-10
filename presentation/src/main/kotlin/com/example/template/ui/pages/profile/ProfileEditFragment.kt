package com.example.template.ui.pages.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.domain.model.User
import com.example.presentation.R
import com.example.presentation.databinding.FragmentProfileEditBinding
import com.example.template.common.BaseFragment
import com.example.template.common.android.ext.view.disable
import com.example.template.common.android.ext.view.enable
import com.example.template.common.arch.viewbinding.LoadingView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProfileEditFragment : BaseFragment(), LoadingView {

    override val loggerTag: String get() = this::class.java.simpleName
    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: ProfileNavigator by lazy { ProfileNavigator(navigationController) }
    private val binding: FragmentProfileEditBinding by viewBinding(FragmentProfileEditBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.profileEditFragment.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.save.disable()
        handleTextChanges()
    }

    private fun handleTextChanges() {
        binding {
            firstName.doAfterTextChanged { check() }
            lastName.doAfterTextChanged { check() }
            dateOfBirth.doAfterTextChanged { check() }
            country.doAfterTextChanged { check() }
            city.doAfterTextChanged { check() }
            postalCode.doAfterTextChanged { check() }
            address.doAfterTextChanged { check() }
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()

        viewModel.userState.observe(viewLifecycleOwner, observeUserState())
        viewModel.loadingProgressEvent.observe(viewLifecycleOwner, observeLoadingProgressEvent())
        viewModel.loadingErrorEvent.observe(viewLifecycleOwner, observeLoadingErrorEvent())
    }

    override fun initClicks() {
        super.initClicks()
        with(binding) {
            save.setOnClickListener {
                viewModel.saveUser()
                navigator.back()
            }
            back.setOnClickListener { navigator.back() }
        }
    }

    private fun check() {
        with(binding) {
            save.enable(
                firstName.isValidString()
                    and lastName.isValidString()
                    and dateOfBirth.isValidString()
                    and dateOfBirth.isValidDate(ProfileViewModel.USER_DATE_FORMAT)
                    and country.isValidString()
                    and city.isValidString()
                    and postalCode.isValidString()
                    and address.isValidString()
            )

            if (save.isEnabled) setUserData()
        }
    }

    private fun observeUserState(): Observer<User> = Observer { showUser(it) }
    private fun observeLoadingProgressEvent(): Observer<Boolean> = Observer { showLoadingIf(it) }
    private fun observeLoadingErrorEvent(): Observer<String?> = Observer { showError(it) }

    private fun setUserData() {
        binding {
            viewModel.setUserFirstName(firstName.text.toString())
            viewModel.setUserLastName(firstName.text.toString())
            viewModel.setUserDateOfBirth(dateOfBirth.text.toString().parseUserDate())
            viewModel.setUserAddress(
                country.text.toString(),
                city.text.toString(),
                postalCode.text.toString().toInt(),
                address.text.toString(),
            )
        }
    }

    private fun showUser(user: User) {
        binding {
            firstName.setText(user.firstName)
            lastName.setText(user.lastName)
            dateOfBirth.setText(user.birthDate.formatUserDate())
            country.setText(user.address.country)
            city.setText(user.address.city)
            postalCode.setText(user.address.postalCode.toString())
            address.setText(user.address.street)
        }
    }

    private fun Long.formatUserDate() = try {
        SimpleDateFormat(ProfileViewModel.USER_DATE_FORMAT, Locale.US).format(this)
    } catch (e: Exception) {
        ""
    }

    private fun String.parseUserDate() =
        SimpleDateFormat(ProfileViewModel.USER_DATE_FORMAT, Locale.US).parse(this)!!.time

    private fun EditText.isValidString(): Boolean {
        if (this.text.toString().isBlank()) {
            this.error = getString(R.string.empty_field_error)
            return false
        } else {
            this.error = null
        }
        if (this.text.toString().length < 3) {
            this.error = getString(R.string.invalid_field_error)
            return false
        } else {
            this.error = null
        }
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun EditText.isValidDate(format: String): Boolean {
        val sdf = SimpleDateFormat(format).apply {
            isLenient = false
        }
        return try {
            sdf.parse(this.text.toString())
            true
        } catch (e: ParseException) {
            false
        }
    }
}

