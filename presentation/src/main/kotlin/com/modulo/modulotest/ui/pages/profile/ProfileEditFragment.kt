package com.modulo.modulotest.ui.pages.profile

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.modulo.domain.model.User
import com.modulo.modulotest.common.BaseFragment
import com.modulo.modulotest.common.android.ext.view.disable
import com.modulo.modulotest.common.android.ext.view.enable
import com.modulo.modulotest.common.arch.viewbinding.LoadingView
import com.modulo.presentation.R
import com.modulo.presentation.databinding.FragmentProfileEditBinding
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

        binding.saveBtn.disable()
        handleTextChanges()
    }

    private fun handleTextChanges() {
        binding {
            firstNameEdt.validateField(ProfileField.FIRST_NAME)
            lastNameEdt.validateField(ProfileField.LAST_NAME)
            dateOfBirthEdt.validateField(ProfileField.DATE_OF_BIRTH)
            countryEdt.validateField(ProfileField.COUNTRY)
            cityEdt.validateField(ProfileField.CITY)
            postalCodeEdt.validateField(ProfileField.POSTAL_CODE)
            addressEdt.validateField(ProfileField.ADDRESS)
            dateOfBirthEdt.doAfterTextChanged {
                viewModel.isValidDate(dateOfBirthEdt.text.toString(), ProfileField.DATE_OF_BIRTH)
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
            saveBtn.setOnClickListener {
                viewModel.saveUser()
                navigator.back()
            }
            backBtn.setOnClickListener { navigator.back() }
        }
    }

    private fun observeTextState(): Observer<Pair<ProfileTextState, ProfileField>> = Observer { handleInputErrors(it) }
    private fun observeUserState(): Observer<User> = Observer { showUser(it) }
    private fun observeLoadingProgressEvent(): Observer<Boolean> = Observer { showLoadingIf(it) }
    private fun observeLoadingErrorEvent(): Observer<String?> = Observer { showError(it) }

    private fun setUserData() {
        binding {
            viewModel.updateUserFirstName(firstNameEdt.text.toString())
            viewModel.updateUserLastName(lastNameEdt.text.toString())
            viewModel.updateUserDateOfBirth(dateOfBirthEdt.text.toString().parseUserDate())
            viewModel.updateUserAddress(
                countryEdt.text.toString(),
                cityEdt.text.toString(),
                postalCodeEdt.text.toString(),
                addressEdt.text.toString(),
            )
        }
    }

    private fun showUser(user: User) {
        binding {
            firstNameEdt.setText(user.firstName)
            lastNameEdt.setText(user.lastName)
            dateOfBirthEdt.setText(user.birthDate.formatUserDate())
            countryEdt.setText(user.address.country)
            cityEdt.setText(user.address.city)
            postalCodeEdt.setText(user.address.postalCode.toString())
            addressEdt.setText(user.address.street)
        }
    }

    private fun handleInputErrors(pair: Pair<ProfileTextState, ProfileField>) {
        binding {
            when (pair.second) {
                ProfileField.FIRST_NAME -> firstNameEdt.handleState(pair.first)
                ProfileField.LAST_NAME -> lastNameEdt.handleState(pair.first)
                ProfileField.DATE_OF_BIRTH -> dateOfBirthEdt.handleState(pair.first)
                ProfileField.COUNTRY -> countryEdt.handleState(pair.first)
                ProfileField.CITY -> cityEdt.handleState(pair.first)
                ProfileField.POSTAL_CODE -> postalCodeEdt.handleState(pair.first)
                ProfileField.ADDRESS -> addressEdt.handleState(pair.first)
            }
        }
        check()
    }

    private fun check() {
        with(binding) {
            saveBtn.enable(
                firstNameEdt.gotError()
                    and lastNameEdt.gotError()
                    and dateOfBirthEdt.gotError()
                    and countryEdt.gotError()
                    and cityEdt.gotError()
                    and postalCodeEdt.gotError()
                    and addressEdt.gotError()
            )

            if (saveBtn.isEnabled) setUserData()
        }
    }

    private fun Long.formatUserDate() = try {
        SimpleDateFormat(ProfileViewModel.USER_DATE_FORMAT, Locale.getDefault()).format(this)
    } catch (e: Exception) {
        ""
    }

    private fun String.parseUserDate() =
        if (this.isNotBlank()) SimpleDateFormat(
            ProfileViewModel.USER_DATE_FORMAT,
            Locale.getDefault()
        ).parse(this)!!.time
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

