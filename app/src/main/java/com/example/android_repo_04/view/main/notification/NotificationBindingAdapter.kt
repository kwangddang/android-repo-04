package com.example.android_repo_04.view.main.notification

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.utils.DataResponse
import com.example.android_repo_04.utils.dateToFormattedString
import com.example.android_repo_04.utils.urlToBitmap
import kotlinx.coroutines.*

object NotificationBindingAdapter {

    @JvmStatic
    @BindingAdapter("notificationOwnerAvatar", "viewModel")
    fun setNotificationOwnerAvatar(view: ImageView, avatarUrl: String?, viewModel: ViewModel) {
        urlToBitmap(avatarUrl.orEmpty(), viewModel) { bitmap ->
            view.setImageBitmap(bitmap)
        }
    }

    @JvmStatic
    @BindingAdapter("notificationDate")
    fun setNotificationDate(view: TextView, dateString: String) {
        view.text = dateToFormattedString(dateString)
    }

    @JvmStatic
    @BindingAdapter("notificationComments", "viewModel")
    fun setNotificationComments(view: TextView, fullName: String, viewModel: ViewModel) {
        viewModel.viewModelScope.launch {
            val split = fullName.split('/')
            GitHubApiRepository().requestCommentsCount(split[0], split[1]) { response ->
                if(response is DataResponse.Success)
                    view.text = response.data!!.toString()
                else if(response is DataResponse.Error)
                    view.text = "0"
            }
        }
    }
}