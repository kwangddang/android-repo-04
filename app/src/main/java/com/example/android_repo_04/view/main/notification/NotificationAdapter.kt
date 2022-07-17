package com.example.android_repo_04.view.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.databinding.ItemNotificationBinding

class NotificationAdapter(private val viewModel: ViewModel): RecyclerView.Adapter<NotificationsViewHolder>() {

    var notifications = mutableListOf<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        return NotificationsViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.bind(notifications[position],viewModel)
    }

    override fun getItemCount(): Int = notifications.size
}

class NotificationsViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(notification: Notification, viewModel: ViewModel){
        binding.notification = notification
        binding.vm = viewModel
    }
}