package com.example.template.ui.pages.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.domain.model.Device
import com.example.presentation.R
import com.example.presentation.databinding.FragmentTemplateDetailsBinding
import com.example.template.common.BaseFragment
import com.example.template.common.navigation.BackListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeviceDetailsFragment : BaseFragment(), BackListener {

    private val args: DeviceDetailsFragmentArgs by navArgs()
    private val device: Device? by lazy { args.device }

    override val loggerTag: String get() = this::class.java.simpleName
    override val viewModel: TemplateDetailsViewModel by viewModel { parametersOf(device) }
    override val navigator: TemplateDetailsNavigator by lazy { TemplateDetailsNavigator(navigationController) }

    private val binding: FragmentTemplateDetailsBinding by viewBinding(FragmentTemplateDetailsBinding::inflate)

    override fun onApplyScreenInsets() {
        super.onApplyScreenInsets()
        binding.detailsLayout.applyStatusBarInsetWithPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteButton.setOnClickListener {
            dialogDelegate?.showYesNoDialog(
                title = getString(R.string.car_details_delete_title),
                message = getString(R.string.car_details_delete_message),
                yesAction = { viewModel.deleteDetails() }
            )
        }
        binding.backButtonInclude.backButton.setOnClickListener {
            navigator.back()
        }

        observeLiveData()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.updateCompletedEvent.observe(viewLifecycleOwner, observeUpdateCompletion())
        viewModel.updateErrorEvent.observe(viewLifecycleOwner, observeUpdateErrors())
    }

    private fun observeUpdateCompletion(): Observer<Boolean> = Observer {
        navigator.back()
    }

    private fun observeUpdateErrors(): Observer<String?> = Observer {
        showError(it)
    }

}
