package com.example.template.ui.pages.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.DeviceInteractor
import com.example.domain.model.Device
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

class DeviceListViewModel(
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    override val tag: String get() = "ListViewModel"

    private val _carsListState = MutableLiveData<List<Device>>()
    val deviceListState: LiveData<List<Device>> = _carsListState
    private val _loadingProgressEvent = SingleLiveData<Boolean>()
    val loadingProgressEvent: LiveData<Boolean> = _loadingProgressEvent
    private val _loadingErrorEvent = SingleLiveData<String?>()
    val loadingErrorEvent: LiveData<String?> = _loadingErrorEvent

    override fun attach() {
        super.attach()
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.getDevices()) }
                .flowOn(Dispatchers.IO)
                .onStart { _loadingProgressEvent.value = true }
                .catch {
                    _loadingErrorEvent.value = it.message
                    _loadingProgressEvent.value = false
                }
                .collect {
                    _carsListState.value = it
                    if (it.isEmpty()) {
                        initCars()
                    } else {
                        _loadingProgressEvent.value = false
                    }
                }
        }
    }

    private fun initCars() {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.initDevices()) }
                .flowOn(Dispatchers.IO)
                .onStart { _loadingProgressEvent.value = true }
                .onCompletion { _loadingProgressEvent.value = false }
                .catch { _loadingErrorEvent.value = it.message }
                .collect { _carsListState.value = it }
        }
    }
}
