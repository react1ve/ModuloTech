package com.modulo.modulotest.ui.pages.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.modulo.domain.interactor.DeviceInteractor
import com.modulo.domain.model.Device
import com.modulo.modulotest.common.arch.MvvmViewModel
import com.modulo.modulotest.common.arch.SingleLiveData
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

    override val tag: String = this::class.java.simpleName

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

    fun updateIntensity(value: Int) {
        if (device is Device.Light) {
            device.intensity = value
        }
    }

    fun updateTemperature(value: Double) {
        if (device is Device.Heater) {
            device.temperature = value
        }
    }

    fun updatePosition(value: Int) {
        if (device is Device.RollerShutter) {
            device.position = value
        }
    }

    fun updateMode(on: Boolean) {
        when (device) {
            is Device.Light -> device.setChecked(on)
            is Device.Heater -> device.setChecked(on)
            else -> {}
        }
    }

}
