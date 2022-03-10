package com.example.template.ui.pages.profile

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
            firstName.validateField(ProfileField.FIRST_NAME)
            lastName.validateField(ProfileField.LAST_NAME)
            dateOfBirth.validateField(ProfileField.DATE_OF_BIRTH)
            country.validateField(ProfileField.COUNTRY)
            city.validateField(ProfileField.CITY)
            postalCode.validateField(ProfileField.POSTAL_CODE)
            address.validateField(ProfileField.ADDRESS)
            dateOfBirth.doAfterTextChanged {
                viewModel.isValidDate(dateOfBirth.text.toString(), ProfileField.DATE_OF_BIRTH)
            }
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()

        viewModel.userState.observe(viewLifecycleOwner, observeUserState())
        viewModel.profileTextState.observe(viewLifecycleOwner, observeTextState())
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

    private fun observeTextState(): Observer<Pair<ProfileTextState, ProfileField>> = Observer { handleInputErrors(it) }
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
                postalCode.text.toString(),
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

    private fun handleInputErrors(pair: Pair<ProfileTextState, ProfileField>) {
        binding {
            when (pair.second) {
                ProfileField.FIRST_NAME -> firstName.handleState(pair.first)
                ProfileField.LAST_NAME -> lastName.handleState(pair.first)
                ProfileField.DATE_OF_BIRTH -> dateOfBirth.handleState(pair.first)
                ProfileField.COUNTRY -> country.handleState(pair.first)
                ProfileField.CITY -> city.handleState(pair.first)
                ProfileField.POSTAL_CODE -> postalCode.handleState(pair.first)
                ProfileField.ADDRESS -> address.handleState(pair.first)
            }
        }
        check()
    }

    private fun check() {
        with(binding) {
            save.enable(
                firstName.gotError()
                    and lastName.gotError()
                    and dateOfBirth.gotError()
                    and country.gotError()
                    and city.gotError()
                    and postalCode.gotError()
                    and address.gotError()
            )

            if (save.isEnabled) setUserData()
        }
    }

    private fun Long.formatUserDate() = try {
        SimpleDateFormat(ProfileViewModel.USER_DATE_FORMAT, Locale.US).format(this)
    } catch (e: Exception) {
        ""
    }

    private fun String.parseUserDate() =
        if (this.isNotBlank()) SimpleDateFormat(ProfileViewModel.USER_DATE_FORMAT, Locale.US).parse(this)!!.time
        else 0

    private fun EditText.validateField(profileField: ProfileField) {
        this.doAfterTextChanged { viewModel.onTextChanged(this.text.toString(), profileField) }
    }

    private fun EditText.handleState(state: ProfileTextState) {
        this.error = when (state) {
            ProfileTextState.TextEmpty -> getString(R.string.empty_field_error)
            ProfileTextState.TextTooShort -> getString(R.string.invalid_field_error)
            ProfileTextState.ExtraValidation -> getString(R.string.invalid_field_error)
            ProfileTextState.TextOk -> null
        }
    }

    private fun EditText.gotError(): Boolean = this.error == null

    override fun onBackPressed(): Boolean {
        navigator.back()
        return true
    }

}

