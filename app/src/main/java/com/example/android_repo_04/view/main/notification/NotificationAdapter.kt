package com.example.android_repo_04.view.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.search.SearchItem
import com.example.android_repo_04.databinding.ItemNotificationBinding

class NotificationAdapter(private val viewModel: ViewModel): ListAdapter<Notification,NotificationsViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        return NotificationsViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Notification>() {
            override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
                oldItem.id == newItem.id
        }
    }
}

class NotificationsViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(notification: Notification, viewModel: ViewModel){
        binding.notification = notification
        binding.vm = viewModel
    }
}