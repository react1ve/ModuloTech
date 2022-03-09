package com.example.template.common.android.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<Model, ViewHolder : RecyclerView.ViewHolder>(
    hasStableIds: Boolean = false
) : RecyclerView.Adapter<ViewHolder>() {

    protected val items = mutableListOf<Model>()

    init {
        setHasStableIds(hasStableIds)
    }

    final override fun setHasStableIds(hasStableIds: Boolean) = super.setHasStableIds(hasStableIds)

    fun isEmpty() = items.isEmpty()

    override fun getItemCount() = items.size

    open fun getItem(position: Int) = items[position]

    open fun getItemPosition(item: Model): Int = items.indexOf(item)

    open fun addItem(item: Model, notify: Boolean = true) {
        items.add(item)
        if (notify) notifyItemInserted(items.size)
    }

    open fun addItems(newItems: List<Model>, notify: Boolean = true) {
        if (newItems.isNotEmpty()) items.addAll(newItems)
        if (notify) notifyDataSetChanged()
    }

    open fun updateItem(item: Model, notify: Boolean = true) {
        val position = items.indexOf(item)
        if (position >= 0) {
            setItem(position, item, notify)
        } else {
            items.add(item)
            if (notify) notifyItemInserted(items.size)
        }
    }

    open fun updateItems(items: List<Model>?) {
        items
            ?.also {
                if (this.items.isEmpty()) {
                    addItems(items)
                } else {
                    items.forEach { updateItem(it, true) }
                }
            }
            ?: clear(true)
    }

    open fun setItems(items: List<Model>, notify: Boolean = true) {
        clear(false)
        addItems(items, notify)
    }

    open fun setItem(position: Int, item: Model, notify: Boolean = true) {
        items[position] = item
        if (notify) notifyItemChanged(position)
    }

    open fun removeItem(position: Int, notify: Boolean = true) = items.removeAt(position).also {
        if (notify) notifyItemRemoved(position)
    }

    open fun removeItem(item: Model, notify: Boolean = true): Model? = items.indexOf(item)
        .takeIf { it > -1 }
        ?.let { removeItem(it, notify) }

    open fun removeItems(itemsToBeRemoved: List<Model>, notify: Boolean = true) {
        itemsToBeRemoved.forEach { item ->
            items.indexOf(item)
                .takeIf { it > -1 }
                ?.also { removeItem(it, false) }
        }

        if (notify) notifyDataSetChanged()
    }

    open fun clear(notify: Boolean = true) {
        items.clear()
        if (notify) notifyDataSetChanged()
    }
}
