package com.example.template.ui.pages.profile

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.UserInteractor
import com.example.domain.model.Address
import com.example.domain.model.User
import com.example.template.common.arch.MvvmViewModel
import com.example.template.common.arch.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat

class ProfileViewModel(
    private val userInteractor: UserInteractor
) : MvvmViewModel() {

    companion object {
        const val USER_DATE_FORMAT = "dd-MM-yyyy"
        const val MIN_INPUT_REQUIREMENT_LENGTH = 3
    }

    override val tag: String get() = "ListViewModel"

    private var user: User? = null

    private val _userState = MutableLiveData<User>()
    val userState: LiveData<User> = _userState
    private val _profileTextState = MutableLiveData<Pair<ProfileTextState, ProfileField>>()
    val profileTextState: LiveData<Pair<ProfileTextState, ProfileField>> = _profileTextState
    private val _loadingProgressEvent = SingleLiveData<Boolean>()
    val loadingProgressEvent: LiveData<Boolean> = _loadingProgressEvent
    private val _loadingErrorEvent = SingleLiveData<String?>()
    val loadingErrorEvent: LiveData<String?> = _loadingErrorEvent

    override fun attach() {
        super.attach()
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(userInteractor.getUser()) }
                .flowOn(Dispatchers.IO)
                .onStart { _loadingProgressEvent.value = true }
                .catch {
                    _loadingErrorEvent.value = it.message
                    _loadingProgressEvent.value = false
                }
                .collect {
                    if (it == null) {
                        initUsers()
                    } else {
                        user = it
                        _userState.value = it
                        _loadingProgressEvent.value = false
                    }
                }
        }
    }

    private fun initUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(userInteractor.initUsers()) }
                .flowOn(Dispatchers.IO)
                .onStart { _loadingProgressEvent.value = true }
                .onCompletion { _loadingProgressEvent.value = false }
                .catch { _loadingErrorEvent.value = it.message }
                .collect { _userState.value = it }
        }
    }

    fun saveUser() {
        viewModelScope.launch(Dispatchers.Main) {
            user?.let {
                userInteractor.saveUser(it)
            }
            getUser()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            userInteractor.logOut()
        }
    }

    fun setUserFirstName(firstName: String) {
        user = user?.copy(firstName = firstName)
    }

    fun setUserLastName(lastName: String) {
        user = user?.copy(lastName = lastName)
    }

    fun setUserDateOfBirth(birthDate: Long) {
        user = user?.copy(birthDate = birthDate)
    }

    fun setUserAddress(country: String, city: String, postalCode: String, street: String) {
        user = user?.copy(
            address = Address(
                country = country,
                city = city,
                postalCode = if (postalCode.isNotBlank()) postalCode.toInt() else 0,
                street = street
            )
        )
    }

    fun setAppTheme(themeMode: Int) {
        viewModelScope.launch { userInteractor.setAppTheme(themeMode) }
    }

    fun setAppLang(lang: String) {
        viewModelScope.launch { userInteractor.setAppLang(lang) }
    }

    fun onTextChanged(text: String, inputType: ProfileField) {
        when {
            text.isBlank() -> _profileTextState.value = Pair(ProfileTextState.TextEmpty, inputType)
            text.length < MIN_INPUT_REQUIREMENT_LENGTH -> _profileTextState.value =
                Pair(ProfileTextState.TextTooShort, inputType)
            else -> _profileTextState.value = Pair(ProfileTextState.TextOk, inputType)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun isValidDate(text: String, inputType: ProfileField) {
        val sdf = SimpleDateFormat(USER_DATE_FORMAT).apply {
            isLenient = false
        }
        val successFulParse = try {
            sdf.parse(text)
            true
        } catch (e: ParseException) {
            false
        }
        _profileTextState.value =
            Pair(if (successFulParse) ProfileTextState.TextOk else ProfileTextState.ExtraValidation, inputType)
    }

}

enum class ProfileField {
    FIRST_NAME,
    LAST_NAME,
    DATE_OF_BIRTH,
    COUNTRY,
    CITY,
    POSTAL_CODE,
    ADDRESS;
}

sealed class ProfileTextState {
    object TextOk : ProfileTextState()
    object TextEmpty : ProfileTextState()
    object TextTooShort : ProfileTextState()
    object ExtraValidation : ProfileTextState()
}
