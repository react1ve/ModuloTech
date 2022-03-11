package com.modulo.modulotest.ui.pages.profile.bottomsheet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.modulo.presentation.databinding.BottomSheetThemeBinding

class SetThemeBottomSheet(private val listener: (themMode: Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetThemeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lightTv.setOnClickListener {
                dismiss()
                listener(AppCompatDelegate.MODE_NIGHT_NO)
            }
            darkTv.setOnClickListener {
                dismiss()
                listener(AppCompatDelegate.MODE_NIGHT_YES)
            }
            systemTv.setOnClickListener {
                dismiss()
                listener(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}
