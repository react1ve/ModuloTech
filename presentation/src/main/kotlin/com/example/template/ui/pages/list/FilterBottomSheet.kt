package com.example.template.ui.pages.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.presentation.databinding.BottomSheetFilterBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet(
    private val filterList: MutableList<Filter>,
    private val filterListener: (selectedId: Filter?) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFilterBinding? = null
    private val binding: BottomSheetFilterBinding get() = _binding!!

    private lateinit var filterAdapter: FilterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setBackgroundResource(android.R.color.transparent)
        initRecycler()


        binding.reset.setOnClickListener {
            dismiss()
            filterListener.invoke(null)
        }
        binding.apply.setOnClickListener {
            filterListener.invoke(selected)
            dismiss()
        }
    }

    private var selected: Filter? = null
    private fun initRecycler() {
        filterAdapter = FilterAdapter().apply {
            listener = { chosen ->
                filterList.forEach { it.isSelected = false }
                selected = filterList.firstOrNull { it.name == chosen.name }
                selected?.isSelected = true
                setItems(filterList)
            }
            setItems(filterList)
        }
        binding.recycler.adapter = filterAdapter

        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.recycler.layoutManager = layoutManager
    }
}
