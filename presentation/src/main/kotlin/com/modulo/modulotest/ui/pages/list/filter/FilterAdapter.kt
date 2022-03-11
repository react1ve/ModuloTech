package com.modulo.modulotest.ui.pages.list.filter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modulo.modulotest.common.android.adapter.BaseRecyclerAdapter
import com.modulo.modulotest.common.android.ext.view.inflateChild
import com.modulo.presentation.databinding.ItemSelectableTextBinding

data class Filter(val name: String, var isSelected: Boolean = false)

class FilterAdapter(private val listener: ((filter: Filter) -> Unit)) :
    BaseRecyclerAdapter<Filter, FilterAdapter.StatusViewHolder>() {

    inner class StatusViewHolder(
        parent: ViewGroup,
        val binding: ItemSelectableTextBinding = parent.inflateChild(ItemSelectableTextBinding::inflate),
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(filter: Filter) {
            binding.root.setOnClickListener { listener.invoke(filter) }
            binding.nameTv.text = filter.name
            binding.nameTv.isSelected = filter.isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        return StatusViewHolder(parent)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
