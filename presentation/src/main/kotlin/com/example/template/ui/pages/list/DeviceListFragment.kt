package com.example.template.ui.pages.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.domain.model.Device
import com.example.presentation.R
import com.example.presentation.databinding.FragmentDeviceListBinding
import com.example.template.common.BaseFragment
import com.example.template.common.arch.viewbinding.LoadingView
import com.example.template.ui.pages.list.filter.Filter
import com.example.template.ui.pages.list.filter.FilterBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceListFragment : BaseFragment(), LoadingView {

    override val loggerTag: String get() = this::class.java.simpleName
    override val viewModel: DeviceListViewModel by viewModel()
    override val navigator: DeviceListNavigator by lazy { DeviceListNavigator(navigationController) }

    private val binding: FragmentDeviceListBinding by viewBinding(FragmentDeviceListBinding::inflate)

    private val adapter: DevicesAdapter by lazy {
        DevicesAdapter({ device, delete ->
            if (delete) deleteDevice(device)
            else navigator.openDetails(device)
        }, { device -> viewModel.updateDevice(device) })
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

    override fun initClicks() {
        binding {
            filter.setOnClickListener { viewModel.openFilter() }
            settings.setOnClickListener { navigator.openSettings() }
        }
    }

    private fun openFilterBottomSheet(filters: List<Filter>) {
        val bsh = FilterBottomSheet(filters) { filter ->
            viewModel.filterDevices(filter)
        }
        if (!bsh.isAdded) bsh.show(childFragmentManager, "FilterBottomSheet")
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.deviceListState.observe(viewLifecycleOwner, observeDeviceListState())
        viewModel.loadingProgressEvent.observe(viewLifecycleOwner, observeLoadingProgressEvent())
        viewModel.loadingErrorEvent.observe(viewLifecycleOwner, observeLoadingErrorEvent())
        viewModel.filterListState.observe(viewLifecycleOwner, observeFilterListState())
        viewModel.filteredDevices.observe(viewLifecycleOwner, observeFilteredDeviceListState())
    }

    private fun observeFilteredDeviceListState(): Observer<List<Device>> = Observer { showData(it) }
    private fun observeLoadingProgressEvent(): Observer<Boolean> = Observer { showLoadingIf(it) }
    private fun observeLoadingErrorEvent(): Observer<String?> = Observer { showError(it) }
    private fun observeDeviceListState(): Observer<List<Device>> = Observer { showData(it) }
    private fun observeFilterListState(): Observer<List<Filter>> = Observer { openFilterBottomSheet(it) }

    private fun showData(list: List<Device>) {
        adapter.setData(list)
    }

    private fun deleteDevice(device: Device) {
        dialogDelegate?.showOkCancelDialog(
            title = getString(R.string.delete_title),
            message = getString(R.string.delete_message),
            yesAction = { viewModel.deleteDevice(device) }
        )
    }
}
