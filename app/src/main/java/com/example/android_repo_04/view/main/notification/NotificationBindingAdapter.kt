package com.example.android_repo_04.view.main.notification

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.utils.urlToBitmap
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

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
        val fm = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = fm.parse(dateString)
        val today = Calendar.getInstance()
        val diffMin: Long = (today.time.time - date.time) / 60000
        val diffHour: Long = (today.time.time - date.time) / 3600000

        if(diffHour < 1){
            view.text = "${diffMin}분 전"
        } else if(diffHour < 24){
            view.text = "${diffHour}시간 전"
        } else if(diffHour < 24 * 30){
            view.text = "${diffHour / 24}일 전"
        } else if(diffHour < 24 * 30 * 12){
            view.text = "${diffHour / (24 * 30)}달 전"
        } else {
            view.text = "${diffHour / (24 * 30 * 12)}년 전"
        }
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