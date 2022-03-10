package com.example.template.ui.pages.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.domain.model.Device
import com.example.presentation.R
import com.example.presentation.databinding.FragmentDeviceDetailsBinding
import com.example.template.common.BaseFragment
import com.example.template.common.android.ext.view.onProgressChanged
import com.example.template.common.navigation.BackListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeviceDetailsFragment : BaseFragment(), BackListener {

    private val args: DeviceDetailsFragmentArgs by navArgs()
    private val device: Device by lazy { args.device }

    override val loggerTag: String get() = this::class.java.simpleName
    override val viewModel: DetailsViewModel by viewModel { parametersOf(device) }
    override val navigator: DetailsNavigator by lazy { DetailsNavigator(navigationController) }

    private val binding: FragmentDeviceDetailsBinding by viewBinding(FragmentDeviceDetailsBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.detailsLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }


    override fun initClicks() {
        binding {
            power.isVisible = device is Device.Light || device is Device.Heater
            power.setOnClickListener {
                device.let {
                    when (it) {
                        is Device.Light -> {
                            setPowerMode(!it.isChecked())
                            viewModel.updateDevice()
                        }
                        is Device.Heater -> {
                            setPowerMode(!it.isChecked())
                            viewModel.updateDevice()
                        }
                        else -> {}
                    }
                }
            }
            progressBar.onProgressChanged { progress ->
                device.let {
                    when (it) {
                        is Device.Light -> {
                            current.text = it.intensity.toString()
                            viewModel.setIntensity(progress.toInt())
                        }
                        is Device.Heater -> {
                            current.text = getString(R.string.c, it.temperature)
                            viewModel.setTemperature(progress)
                        }
                        is Device.RollerShutter -> {
                            current.text = it.position.toString()
                            viewModel.setPosition(progress.toInt())
                        }
                    }
                }
                viewModel.updateDevice()
            }
        }
    }

    private fun setData() {
        device.let {
            binding {
                title.text = it.deviceName
                subtitle.text = it.productType.title

                when (it) {
                    is Device.Light -> {
                        initialValue.text = it.intensity.toString()
                        current.text = it.intensity.toString()

                        setPowerMode(it.isChecked())
                    }
                    is Device.Heater -> {
                        initialValue.text = getString(R.string.c, it.temperature)
                        current.text = getString(R.string.c, it.temperature)

                        setPowerMode(it.isChecked())
                    }
                    is Device.RollerShutter -> {
                        initialValue.text = it.position.toString()
                        current.text = it.position.toString()
                        binding.power.isVisible = false
                    }
                }
            }
        }
    }

    private fun setProgressMinMax(min: Int, max: Int) {
        binding {
            progressBar.min = min.toDouble()
            progressBar.max = max.toDouble()
            ruler.setMinMaxValue(min.toFloat(), max.toFloat())
        }
    }

    private fun setPowerMode(on: Boolean) {
        binding.power.setImageResource(if (on) R.drawable.ic_power_red else R.drawable.ic_power_blue)
        viewModel.setMode(on)
    }

    override fun onBackPressed(): Boolean {
        navigator.back()
        return true
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.updateErrorEvent.observe(viewLifecycleOwner, this::showError)
    }

}
