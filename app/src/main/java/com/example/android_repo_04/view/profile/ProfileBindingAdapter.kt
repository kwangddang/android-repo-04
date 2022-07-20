package com.example.android_repo_04.view.profile

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.urlToBitmap
import de.hdodenhof.circleimageview.CircleImageView

object ProfileBindingAdapter {
    @JvmStatic
    @BindingAdapter("profileImage", "viewModel")
    fun setProfileImage(view: CircleImageView, url: String?, viewModel: ViewModel){
        urlToBitmap(url.orEmpty(),viewModel) { bitmap ->
            view.setImageBitmap(bitmap)
        }
    }

    @JvmStatic
    @BindingAdapter("locationText")
    fun setLocationText(view: TextView, text: String?) {
        if(text == null || text == "") {
            view.text = view.context.getString(R.string.no_location)
        } else {
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("blogText")
    fun setBlogText(view: TextView, text: String?) {
        if(text == null || text == "") {
            view.text = view.context.getString(R.string.no_blog)
        } else {
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("mailText")
    fun setMailText(view: TextView, text: String?) {
        if(text == null || text == "") {
            view.text = view.context.getString(R.string.no_email)
        } else {
            view.text = text
        }
    }
}