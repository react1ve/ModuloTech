package com.example.template.ui.pages.profile.bottomsheet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.presentation.databinding.BottomSheetThemeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SetThemeBottomSheet(private val listener: (themMode: Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetThemeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            light.setOnClickListener {
                dismiss()
                listener(AppCompatDelegate.MODE_NIGHT_NO)
            }
            dark.setOnClickListener {
                dismiss()
                listener(AppCompatDelegate.MODE_NIGHT_YES)
            }
            system.setOnClickListener {
                dismiss()
                listener(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}
