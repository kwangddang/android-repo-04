package com.example.android_repo_04.view.search

import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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

    @JvmStatic
    @BindingAdapter("searchText")
    fun setSearchText(view: EditText, text: String){}

    @JvmStatic
    @BindingAdapter("searchTextAttrChanged")
    fun setSearchTextInverseBindingListener(view: EditText, listener: InverseBindingListener) {
        view.doAfterTextChanged { listener.onChange() }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "searchText", event = "searchTextAttrChanged")
    fun getSearchText(view: EditText): String = view.text.toString()
}