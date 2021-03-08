package com.example.alarm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alarm.data.model.Alarm
import com.example.alarm.databinding.ItemAlarmBinding

class AlarmAdapter(private val listener: Callback) :
    ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAlarmBinding.inflate(layoutInflater, parent, false)
        val viewHolder = AlarmViewHolder(binding)
        binding.apply {
            timeTv.setOnClickListener { listener.onTimeClicked(viewHolder.adapterPosition) }
            toggleSwitch.setOnClickListener { listener.onToggleSwitchClicked(viewHolder.adapterPosition) }
            moreOptions.setOnClickListener { listener.onMoreOptionsClicked(viewHolder.adapterPosition, binding.moreOptions) }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlarmViewHolder(private val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Alarm) {
            binding.apply {
                alarm = item
                executePendingBindings()
            }
        }
    }

    class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }
    }

    interface Callback {
        fun onTimeClicked(position: Int)
        fun onToggleSwitchClicked(position: Int)
        fun onMoreOptionsClicked(position: Int, view: View)
    }
}
