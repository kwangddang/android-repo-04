package com.example.android_repo_04.view.search

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.ViewModel
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.urlToBitmap
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("searchOwnerAvatar", "viewModel")
fun ImageView.setNotificationOwnerAvatar(avatarUrl: String?, viewModel: ViewModel) {
    urlToBitmap(avatarUrl.orEmpty(), viewModel) { bitmap ->
        setImageBitmap(bitmap)
    }
}

@BindingAdapter("languageColor")
fun CircleImageView.setLanguageColor(language: String?) {
    if (language != null) {
        val color = when (language) {
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
        setImageResource(color)
    } else {
        setImageResource(R.color.transparent)
    }
}

@BindingAdapter("starCountFormat")
fun TextView.setStarCountFormat(count: Int) {
    if (count / 1000000 > 0) {
        text = "${count / 1000000}.${count % 1000000 / 100000}m"
    } else if (count / 1000 > 0) {
        text = "${count / 1000}.${count % 1000 / 100}k"
    } else {
        text = count.toString()
    }
}

@BindingAdapter("searchText")
fun EditText.setSearchText(text: String) {
}

@BindingAdapter("searchTextAttrChanged")
fun EditText.setSearchTextInverseBindingListener(listener: InverseBindingListener) {
    doAfterTextChanged { listener.onChange() }
}

@InverseBindingAdapter(attribute = "searchText", event = "searchTextAttrChanged")
fun EditText.getSearchText(): String = text.toString()
