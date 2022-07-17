package com.example.android_repo_04.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

val savedAvatars = mutableMapOf<String, Bitmap>()

fun urlToBitmap(url: String, viewModel: ViewModel, completed: (Bitmap?) -> Unit) {
    if (url == "") {
        completed(null)
        return
    }

    if (savedAvatars.containsKey(url)) {
        completed(savedAvatars[url])
        return
    }

    viewModel.viewModelScope.launch(Dispatchers.IO) {
        try {
            val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
            savedAvatars[url] = bitmap
            withContext(Dispatchers.Main) {
                completed(bitmap)
            }
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                completed(null)
            }
        }
    }
}