package com.example.android_repo_04.view.profile

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

object ProfileBindingAdapter {
    @JvmStatic
    @BindingAdapter("profileImage")
    fun setProfileImage(view: CircleImageView, url: String?){
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("locationText")
    fun setLocationText(view: TextView, text: String?) {
        if(text == null || text == "") {
            view.text = "No Location"
        } else {
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("blogText")
    fun setBlogText(view: TextView, text: String?) {
        if(text == null || text == "") {
            view.text = "No Blog"
        } else {
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("mailText")
    fun setMailText(view: TextView, text: String?) {
        if(text == null || text == "") {
            view.text = "No Email"
        } else {
            view.text = text
        }
    }
}