package com.example.android_repo_04.view.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.notificaiton.Notification
import com.example.android_repo_04.databinding.ItemNotificationBinding

class NotificationAdapter: RecyclerView.Adapter<NotificationsViewHolder>() {

    var notifications = listOf<Notification>()

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
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size
}

class NotificationsViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(notification: Notification){
        binding.imgNotificationProfile
    }
}