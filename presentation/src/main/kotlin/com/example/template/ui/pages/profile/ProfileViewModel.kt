package com.example.template.ui.pages.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.UserInteractor
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

class ProfileViewModel(
    private val userInteractor: UserInteractor
) : MvvmViewModel() {

    companion object {
        const val USER_DATE_FORMAT = "dd-MM-yyyy"
    }

    override val tag: String get() = "ListViewModel"

    private val _userState by lazy { MutableLiveData<User>() }
    val userState: LiveData<User> = _userState
    private val _loadingProgressEvent by lazy { SingleLiveData<Boolean>() }
    val loadingProgressEvent: LiveData<Boolean> = _loadingProgressEvent
    private val _loadingErrorEvent by lazy { SingleLiveData<String?>() }
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

    fun saveUser(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            userInteractor.saveUser(user)
            getUser()
        }
    }

}
