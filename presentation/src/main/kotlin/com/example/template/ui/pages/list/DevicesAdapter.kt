package com.example.template.ui.pages.list

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Device
import com.example.presentation.R
import com.example.presentation.databinding.ItemDeviceBinding
import com.example.template.common.android.ext.view.autoNotify
import com.example.template.common.android.ext.view.inflateChild

class DevicesAdapter(
    private val selectedItem: (device: Device, delete: Boolean) -> Unit,
    private val changeMode: (device: Device) -> Unit
) : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

    private var items = listOf<Device>()
    fun setData(data: List<Device>) {
        autoNotify(items, data) { old, new ->
            val extra = when {
                old is Device.Light && new is Device.Light -> old.mode == new.mode && old.intensity == new.intensity
                old is Device.Heater && new is Device.Heater -> old.mode == new.mode && old.temperature == new.temperature
                old is Device.RollerShutter && new is Device.RollerShutter -> old.position == new.position
                else -> true
            }

            old.id == new.id
                && old.deviceName == new.deviceName
                && old.productType == new.productType
                && extra
        }
        items = data
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder = DeviceViewHolder(parent)

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class DeviceViewHolder(
        parent: ViewGroup,
        val binding: ItemDeviceBinding = parent.inflateChild(ItemDeviceBinding::inflate),
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Device) = with(itemView) {
            when (item) {
                is Device.Light -> {
                    binding.switchChecked.apply {
                        isVisible = true
                        isChecked = item.isChecked()
                        setOnCheckedChangeListener { _, isChecked ->
                            item.setChecked(isChecked)
                            changeMode.invoke(item)
                        }
                    }
                }
                is Device.Heater -> {
                    binding.switchChecked.apply {
                        isVisible = true
                        isChecked = item.isChecked()
                        setOnCheckedChangeListener { _, isChecked ->
                            item.setChecked(isChecked)
                            changeMode.invoke(item)
                        }
                    }
                }
                is Device.RollerShutter -> {
                    binding.switchChecked.isVisible = false
                }
            }

            binding.apply {
                root.setOnClickListener { selectedItem.invoke(item, false) }
                delete.setOnClickListener { selectedItem.invoke(item, true) }
                brandTextView.text = item.deviceName
                modelTextView.text = item.productType.title
                iconImg.setImageResource(getRandomImg(item, absoluteAdapterPosition))
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
    private fun getRandomImg(item: Device, pos: Int): Int {
        list = when (item) {
            is Device.Light -> lightBulbs
            is Device.Heater -> heaters
            is Device.RollerShutter -> rollerShutters
        }
        return list[pos % list.size]
    }
}
