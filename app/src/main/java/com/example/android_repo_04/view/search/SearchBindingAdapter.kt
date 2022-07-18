package com.example.android_repo_04.view.search

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.example.android_repo_04.utils.urlToBitmap

object SearchBindingAdapter {

    @JvmStatic
    @BindingAdapter("searchOwnerAvatar", "viewModel")
    fun setNotificationOwnerAvatar(view: ImageView, avatarUrl: String, viewModel: ViewModel) {
        urlToBitmap(avatarUrl, viewModel) {
            view.setImageBitmap(it)
        }
    }
}