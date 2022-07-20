package com.example.android_repo_04.view.search

import android.content.Context
import android.graphics.Color
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.ViewModel
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.urlToBitmap
import de.hdodenhof.circleimageview.CircleImageView

object SearchBindingAdapter {

    @JvmStatic
    @BindingAdapter("searchOwnerAvatar", "viewModel")
    fun setNotificationOwnerAvatar(view: ImageView, avatarUrl: String?, viewModel: ViewModel) {
        urlToBitmap(avatarUrl.orEmpty(), viewModel) { bitmap ->
            view.setImageBitmap(bitmap)
        }
    }

    @JvmStatic
    @BindingAdapter("languageColor")
    fun setLanguageColor(view: CircleImageView, language: String?) {
        if(language != null){
            val color = when(language) {
                "JavaScript" -> R.color.java_script
                "HTML" -> R.color.html
                "Java" -> R.color.java
                "Python" -> R.color.python
                "Jupyter Notebook" -> R.color.jupyter_notebook
                "C" -> R.color.c
                "C#" -> R.color.c_sharp
                "CSS" -> R.color.css
                "C++" -> R.color.c_plus
                "Dart" -> R.color.dart
                "PHP" -> R.color.php
                "TypeScript" -> R.color.type_script
                "Kotlin" -> R.color.kotlin
                else -> R.color.white
            }
            view.setImageResource(color)
        } else {
            view.setImageResource(R.color.transparent)
        }
    }

    @JvmStatic
    @BindingAdapter("starCountFormat")
    fun setStarCountFormat(view: TextView, count: Int) {
        if(count / 1000000 > 0) {
            view.text = "${count / 1000000}.${count % 1000000 / 100000}m"
        } else if(count / 1000 > 0) {
            view.text = "${count / 1000}.${count % 1000 / 100}k"
        } else {
            view.text = count.toString()
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