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

@BindingAdapter("notificationComments", "viewModel")
fun TextView.setNotificationComments(fullName: String, viewModel: ViewModel) {
    viewModel.viewModelScope.launch {
        val split = fullName.split('/')
        GitHubApiRepository().requestCommentsCount(split[0], split[1]) { response ->
            if (response is DataResponse.Success)
                text = response.data!!.toString()
            else if (response is DataResponse.Error)
                text = "0"
        }
    }
}
