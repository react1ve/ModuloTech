package com.example.template.ui.pages.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemSelectableTextBinding
import com.example.template.common.android.adapter.BaseRecyclerAdapter
import com.example.template.common.android.ext.view.inflateChild

data class Filter(val name: String, var isSelected: Boolean = false)
class FilterAdapter : BaseRecyclerAdapter<Filter,FilterAdapter.StatusViewHolder>() {
    var listener:((filter:Filter)->Unit)? = null

    inner class StatusViewHolder(
        parent: ViewGroup,
        val binding: ItemSelectableTextBinding = parent.inflateChild(ItemSelectableTextBinding::inflate),
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(filter: Filter){
            binding.root.setOnClickListener { listener?.invoke(filter) }
            binding.name.text = filter.name
            binding.name.isSelected = filter.isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        return StatusViewHolder(parent)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
