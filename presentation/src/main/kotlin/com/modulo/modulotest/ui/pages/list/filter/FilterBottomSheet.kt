package com.modulo.modulotest.ui.pages.list.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.modulo.presentation.databinding.BottomSheetFilterBinding

class FilterBottomSheet(
    private val filterList: List<Filter>, private val filterListener: (selectedIds: List<String>?) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFilterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setBackgroundResource(android.R.color.transparent)

        initRecycler()
        initClicks()
    }

    private fun initClicks() {

        binding.reset.setOnClickListener {
            dismiss()
            filterListener.invoke(null)
        }
        binding.apply.setOnClickListener {
            filterListener.invoke(filterList.filter { it.isSelected }.map { it.name })
            dismiss()
        }
    }

    private lateinit var adapter: FilterAdapter
    private fun initRecycler() {
        adapter = FilterAdapter { chosen ->
            filterList.firstOrNull { it.name == chosen.name }?.let {
                it.isSelected = !it.isSelected
            }
            adapter.setItems(filterList)
        }.apply {
            setItems(filterList)
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
    }
}
