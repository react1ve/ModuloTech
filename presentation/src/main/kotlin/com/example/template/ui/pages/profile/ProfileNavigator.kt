package com.example.template.ui.pages.profile

import androidx.navigation.NavController
import com.example.template.common.navigation.Navigator

class ProfileNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun openEditProfile() =
        navigationController.navigate(
            ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment()
        )

    fun back() = navigationController.navigateUp()

}
