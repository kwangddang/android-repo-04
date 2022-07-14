package com.example.android_repo_04.view.main.notification

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.utils.dateToFormattedString
import com.example.android_repo_04.utils.urlToBitmap
import kotlinx.coroutines.*

object NotificationBindingAdapter {

    @JvmStatic
    @BindingAdapter("notificationOwnerAvatar")
    fun setNotificationOwnerAvatar(view: ImageView, avatarUrl: String) {
        urlToBitmap(avatarUrl) {
            view.setImageBitmap(it)
        }
    }

    @JvmStatic
    @BindingAdapter("notificationDate")
    fun setNotificationDate(view: TextView, dateString: String) {
        view.text = dateToFormattedString(dateString)
    }

    @JvmStatic
    @BindingAdapter("notificationComments")
    fun setNotificationComments(view: TextView, fullName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val split = fullName.split('/')
            GitHubApiRepository().requestCommentsCount(split[0], split[1]) {
                view.text = it.body()!!.size.toString()
            }
        }
    }
}