package com.modulo.modulotest.ui.pages.details

import androidx.navigation.NavController
import com.modulo.modulotest.common.navigation.Navigator

class DetailsNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun back() = navigationController.navigateUp()
}
