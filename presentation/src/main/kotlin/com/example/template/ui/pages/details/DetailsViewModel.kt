package com.example.template.ui.pages.details

import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val device: Device,
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    override val tag: String = "DetailsViewModel"

    private val _updateErrorEvent = SingleLiveData<String?>()
    val updateErrorEvent: LiveData<String?> get() = _updateErrorEvent

    fun updateDevice() {
        viewModelScope.launch(Dispatchers.Main) {
            flow { emit(deviceInteractor.updateDevice(device)) }
                .flowOn(Dispatchers.IO)
                .catch { _updateErrorEvent.value = it.message }
                .collect()
        }
    }

    fun setIntensity(value: Int) {
        if (device is Device.Light) {
            device.intensity = value
        }
    }

    fun setTemperature(value: Double) {
        if (device is Device.Heater) {
            device.temperature = value
        }
    }

    fun setPosition(value: Int) {
        if (device is Device.RollerShutter) {
            device.position = value
        }
    }

    fun setMode(on: Boolean) {
        when (device) {
            is Device.Light -> device.setChecked(on)
            is Device.Heater -> device.setChecked(on)
            else -> {}
        }
    }

}
