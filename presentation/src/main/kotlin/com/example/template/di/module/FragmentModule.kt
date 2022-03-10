package com.example.template.di.module

import com.example.domain.model.Device
import com.example.template.ui.pages.details.DetailsViewModel
import com.example.template.ui.pages.details.DeviceDetailsFragment
import com.example.template.ui.pages.list.DeviceListFragment
import com.example.template.ui.pages.list.DeviceListViewModel
import com.example.template.ui.pages.profile.ProfileEditFragment
import com.example.template.ui.pages.profile.ProfileFragment
import com.example.template.ui.pages.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val deviceListFragmentModule = module {
    scope<DeviceListFragment> {
        viewModel {
            DeviceListViewModel(deviceInteractor = get())
        }
    }
}

internal val deviceDetailsFragmentModule = module {
    scope<DeviceDetailsFragment> {
        viewModel { (device: Device) ->
            DetailsViewModel(device, deviceInteractor = get())
        }
    }
}


internal val profileFragmentModule = module {
    scope<ProfileFragment> {
        viewModel { ProfileViewModel(userInteractor = get()) }
    }
}

internal val profileEditFragmentModule = module {
    scope<ProfileEditFragment> {
        viewModel { ProfileViewModel(userInteractor = get()) }
    }
}
