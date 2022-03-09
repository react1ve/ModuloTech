package com.example.template.ui.pages.list

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Device
import com.example.presentation.R
import com.example.presentation.databinding.ItemDeviceBinding
import com.example.template.common.android.adapter.BaseRecyclerAdapter
import com.example.template.common.android.ext.view.inflateChild
import kotlin.random.Random

class DevicesAdapter(private val selectedItem: (Device) -> Unit) :
    BaseRecyclerAdapter<Device, DevicesAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder =
        CarViewHolder(parent)

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CarViewHolder(
        parent: ViewGroup,
        val binding: ItemDeviceBinding = parent.inflateChild(ItemDeviceBinding::inflate),
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Device) = with(itemView) {
            binding.root.setOnClickListener { selectedItem.invoke(item) }
            binding.brandTextView.text = item.deviceName
            binding.modelTextView.text = item.productType.title
            binding.iconImg.setImageResource(getRandomImg(item))

            when (item) {
                is Device.Light -> {
                    binding.switchChecked.isVisible = true
                    binding.switchChecked.isChecked = item.isChecked()
                }
                is Device.Heater -> {
                    binding.switchChecked.isVisible = true
                    binding.switchChecked.isChecked = item.isChecked()
                }
                is Device.RollerShutter -> {
                    binding.switchChecked.isVisible = false
                }
            }
        }
    }

    private val lightBulbs = listOf(
        R.drawable.ic_light_bulb_1,
        R.drawable.ic_light_bulb_2,
        R.drawable.ic_light_bulb_3
    )

    private val heaters = listOf(
        R.drawable.ic_heater_1,
        R.drawable.ic_heater_2,
        R.drawable.ic_heater_3
    )


    private val rollerShutters = listOf(
        R.drawable.ic_roller_shutter_1,
        R.drawable.ic_roller_shutter_2,
        R.drawable.ic_roller_shutter_3
    )

    private var list = emptyList<Int>()
    private fun getRandomImg(item: Device): Int {
        list = when (item) {
            is Device.Light -> lightBulbs
            is Device.Heater -> heaters
            is Device.RollerShutter -> rollerShutters
        }
        return list[Random.nextInt(0, list.size) % list.size]
    }
}
