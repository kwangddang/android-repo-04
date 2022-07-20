package com.example.android_repo_04.view.profile

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.urlToBitmap
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("profileImage", "viewModel")
fun CircleImageView.setProfileImage(url: String?, viewModel: ViewModel) {
    urlToBitmap(url.orEmpty(), viewModel) { bitmap ->
        setImageBitmap(bitmap)
    }
}

@BindingAdapter("locationText")
fun TextView.setLocationText(location: String?) {
    if (location == null || location == "") {
        text = context.getString(R.string.no_location)
    } else {
        text = location
    }
}

@BindingAdapter("blogText")
fun TextView.setBlogText(blog: String?) {
    if (blog == null || blog == "") {
        text = context.getString(R.string.no_blog)
    } else {
        text = blog
    }
}

@BindingAdapter("mailText")
fun TextView.setMailText(mail: String?) {
    if (mail == null || mail == "") {
        text = context.getString(R.string.no_email)
    } else {
        text = mail
    }
}
