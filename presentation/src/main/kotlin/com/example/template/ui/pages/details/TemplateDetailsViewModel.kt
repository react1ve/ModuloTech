package com.example.template.ui.pages.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.DeviceInteractor
import com.example.domain.model.Device
import com.example.template.common.android.ResourceProvider
import com.example.template.common.arch.MvvmViewModel
import com.example.template.common.arch.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class TemplateDetailsViewModel(
    private val device: Device,
    private val resourceProvider: ResourceProvider,
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    override val tag: String = "DetailsViewModel"

    private val _updateCompletedEvent by lazy { SingleLiveData<Boolean>() }
    val updateCompletedEvent: LiveData<Boolean> get() = _updateCompletedEvent

    private val _updateErrorEvent by lazy { SingleLiveData<String?>() }
    val updateErrorEvent: LiveData<String?> get() = _updateErrorEvent

    fun deleteDetails() = deleteCar(device.id)

    private fun deleteCar(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            flowOf(deviceInteractor.deleteDevice(id))
                .flowOn(Dispatchers.IO)
                .onCompletion { _updateCompletedEvent.value = true }
                .catch { _updateErrorEvent.value = it.message }
                .collect()
        }
    }
/*
    private fun updateCar() {
        viewModelScope.launch(Dispatchers.Main) {
            flow {
                currentStateEntity.mutableCar.asCar()
                    ?.let { emit(deviceInteractor.updateDevice(it)) }
                    ?: throw Exception(resourceProvider.getString(R.string.car_details_validation_error))
            }
                .flowOn(Dispatchers.IO)
                .onCompletion { _updateCompletedEvent.value = true }
                .catch { _updateErrorEvent.value = it.message }
                .collect()
        }
    }*/

}
