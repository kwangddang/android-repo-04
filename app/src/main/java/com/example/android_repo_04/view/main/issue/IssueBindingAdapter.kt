package com.example.android_repo_04.view.main.issue

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.dateToFormattedString
import java.text.SimpleDateFormat
import java.util.*

object IssueBindingAdapter {

    @JvmStatic
    @BindingAdapter("issueState")
    fun setIssueState(view: ImageView, state: String){
        val drawableId = if(state == view.context.getString(R.string.state_open)) {
            R.drawable.ic_issue_open
        } else {
            R.drawable.ic_issue_closed
        }
        Glide.with(view.context)
            .load(drawableId)
            .override(48, 48)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("issueDate")
    fun setIssueDate(view: TextView, dateString: String){
        view.text = dateToFormattedString(dateString)
    }
}