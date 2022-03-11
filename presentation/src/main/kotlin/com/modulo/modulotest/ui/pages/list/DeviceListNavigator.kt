package com.modulo.modulotest.ui.pages.list

import androidx.navigation.NavController
import com.modulo.domain.model.Device
import com.modulo.modulotest.common.navigation.Navigator

class DeviceListNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun openDetails(device: Device) =
        navigationController.navigate(
            DeviceListFragmentDirections.actionDeviceListFragmentToDeviceDetailsFragment(device)
        )

    fun openSettings() =
        navigationController.navigate(
            DeviceListFragmentDirections.actionDeviceListFragmentToProfileFragment()
        )

}
