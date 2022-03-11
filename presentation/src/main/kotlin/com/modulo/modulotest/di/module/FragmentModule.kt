package com.modulo.modulotest.di.module

import com.modulo.domain.model.Device
import com.modulo.modulotest.ui.pages.details.DetailsViewModel
import com.modulo.modulotest.ui.pages.details.DeviceDetailsFragment
import com.modulo.modulotest.ui.pages.list.DeviceListFragment
import com.modulo.modulotest.ui.pages.list.DeviceListViewModel
import com.modulo.modulotest.ui.pages.profile.ProfileEditFragment
import com.modulo.modulotest.ui.pages.profile.ProfileFragment
import com.modulo.modulotest.ui.pages.profile.ProfileViewModel
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
