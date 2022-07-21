package com.example.android_repo_04.view.main.notification

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.utils.DataResponse
import com.example.android_repo_04.utils.dateToFormattedString
import com.example.android_repo_04.utils.urlToBitmap
import kotlinx.coroutines.launch

@BindingAdapter("notificationOwnerAvatar", "viewModel")
fun ImageView.setNotificationOwnerAvatar(avatarUrl: String?, viewModel: ViewModel) {
    urlToBitmap(avatarUrl.orEmpty(), viewModel) { bitmap ->
        setImageBitmap(bitmap)
    }
}

@BindingAdapter("notificationDate")
fun TextView.setNotificationDate(dateString: String) {
    text = dateToFormattedString(dateString)
}

private val commentCache = mutableMapOf<Long,Int>()
@BindingAdapter("notificationComments", "viewModel")
fun TextView.setNotificationComments(notification: Notification, viewModel: ViewModel) {
    if(commentCache.containsKey(notification.id)) {
        text = commentCache[notification.id].toString()
        return
    }
    viewModel.viewModelScope.launch {
        val split = notification.repository.fullName.split('/')
        GitHubApiRepository().requestCommentsCount(split[0], split[1]) { response ->
            if (response is DataResponse.Success) {
                commentCache[notification.id] = response.data!!
                text = response.data.toString()
            }
            else if (response is DataResponse.Error)
                text = "0"
        }
    }
}
