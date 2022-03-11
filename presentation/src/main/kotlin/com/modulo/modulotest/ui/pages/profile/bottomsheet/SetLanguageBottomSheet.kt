package com.modulo.modulotest.ui.pages.profile.bottomsheet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.modulo.presentation.databinding.BottomSheetLanguageBinding

class SetLanguageBottomSheet(private val listener: (lang: String) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLanguageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            french.setOnClickListener {
                dismiss()
                listener("fr")
            }
            english.setOnClickListener {
                dismiss()
                listener("eng")
            }
        }
    }

}
