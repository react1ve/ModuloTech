package com.example.template.ui.pages.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.DeviceInteractor
import com.example.domain.model.Device
import com.example.template.common.arch.MvvmViewModel
import com.example.template.common.arch.SingleLiveData
import com.example.template.ui.pages.list.filter.Filter
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

    private val _deviceListState = MutableLiveData<List<Device>>()
    val deviceListState: LiveData<List<Device>> = _deviceListState
    private val _filterListState = SingleLiveData<List<Filter>>()
    val filterListState: SingleLiveData<List<Filter>> = _filterListState
    private val _filteredDevices = MutableLiveData<List<Device>>()
    val filteredDevices: LiveData<List<Device>> = _filteredDevices
    private val _loadingProgressEvent = SingleLiveData<Boolean>()
    val loadingProgressEvent: LiveData<Boolean> = _loadingProgressEvent
    private val _loadingErrorEvent = SingleLiveData<String?>()
    val loadingErrorEvent: LiveData<String?> = _loadingErrorEvent

    override fun attach() {
        super.attach()
        getDevices()
    }

    private var deviceList = listOf<Device>()
    private fun getDevices(showLoading: Boolean = true) {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.getDevices()) }
                .flowOn(Dispatchers.IO)
                .onStart { _loadingProgressEvent.value = showLoading }
                .catch {
                    _loadingErrorEvent.value = it.message
                    _loadingProgressEvent.value = false
                }
                .collect {
                    deviceList = it
                    _deviceListState.value = it
                    if (it.isEmpty()) {
                        initialize()
                    } else {
                        _loadingProgressEvent.value = false
                    }
                }
        }
    }

    private fun initialize() {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.initDevices()) }
                .flowOn(Dispatchers.IO)
                .onStart { _loadingProgressEvent.value = true }
                .onCompletion { _loadingProgressEvent.value = false }
                .catch { _loadingErrorEvent.value = it.message }
                .collect { _deviceListState.value = it }
        }
    }

    fun deleteDevice(device: Device) {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.deleteDevice(device.id)) }
                .flowOn(Dispatchers.IO)
                .onCompletion { _loadingProgressEvent.value = false }
                .catch { _loadingErrorEvent.value = it.message }
                .collect { getDevices(false) }
        }
    }

    fun updateDevice(device: Device) {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.updateDevice(device)) }
                .flowOn(Dispatchers.IO)
                .onCompletion { _loadingProgressEvent.value = false }
                .catch { _loadingErrorEvent.value = it.message }
                .collect()
        }
    }

    fun openFilter() {
        _filterListState.value = deviceList.map { Filter(it.productType.title, false) }.distinct()
    }

    fun filterDevices(filter: List<String>?) {
        _filteredDevices.value =
            if (filter == null) deviceList else deviceList.filter { it.productType.title in filter }
    }
}
