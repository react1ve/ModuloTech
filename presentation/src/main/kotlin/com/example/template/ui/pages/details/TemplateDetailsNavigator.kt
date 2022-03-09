package com.example.template.ui.pages.details

import androidx.navigation.NavController
import com.example.template.common.navigation.Navigator

class TemplateDetailsNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun back() = navigationController.navigateUp()
}
