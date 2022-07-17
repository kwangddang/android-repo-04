package com.example.android_repo_04.view.main.notification

import androidx.recyclerview.widget.RecyclerView

interface NotificationSwipeListener {
    fun swipe(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
}