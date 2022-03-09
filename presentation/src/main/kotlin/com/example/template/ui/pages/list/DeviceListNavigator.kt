package com.example.template.ui.pages.list

import androidx.navigation.NavController
import com.example.domain.model.Device
import com.example.template.common.navigation.Navigator

class DeviceListNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun openDetails(device: Device) =
        navigationController.navigate(
            DeviceListFragmentDirections.actionTemplateListFragmentToTemplateDetailsFragment(device)
        )
}
