package com.modulo.modulotest.ui.pages.profile

import androidx.navigation.NavController
import com.modulo.modulotest.common.navigation.Navigator

class ProfileNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun openEditProfile() =
        navigationController.navigate(
            ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment()
        )

    fun back() = navigationController.navigateUp()

}
