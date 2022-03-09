package com.example.template.ui.pages.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.domain.model.Device
import com.example.presentation.databinding.FragmentDeviceListBinding
import com.example.template.common.BaseFragment
import com.example.template.common.arch.viewbinding.LoadingView
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceListFragment : BaseFragment(), LoadingView {

    override val loggerTag: String get() = this::class.java.simpleName
    override val viewModel: DeviceListViewModel by viewModel()
    override val navigator: DeviceListNavigator by lazy { DeviceListNavigator(navigationController) }

    private val binding: FragmentDeviceListBinding by viewBinding(FragmentDeviceListBinding::inflate)

    private val adapter: DevicesAdapter by lazy {
        DevicesAdapter { navigator.openDetails(it) }
    }

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.listLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.deviceListState.observe(viewLifecycleOwner, observeCarsListState())
        viewModel.loadingProgressEvent.observe(viewLifecycleOwner, observeLoadingProgressEvent())
        viewModel.loadingErrorEvent.observe(viewLifecycleOwner, observeLoadingErrorEvent())
    }

    private fun observeCarsListState(): Observer<List<Device>> = Observer { showCars(it) }
    private fun observeLoadingProgressEvent(): Observer<Boolean> = Observer { showLoadingIf(it) }
    private fun observeLoadingErrorEvent(): Observer<String?> = Observer { showError(it) }

    private fun showCars(list: List<Device>) {
        adapter.updateItems(list)
    }
}
