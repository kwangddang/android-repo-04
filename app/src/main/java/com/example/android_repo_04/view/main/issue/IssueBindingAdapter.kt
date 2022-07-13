package com.example.android_repo_04.view.main.issue

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android_repo_04.R
import java.text.SimpleDateFormat
import java.util.*

object IssueBindingAdapter {

    @BindingAdapter("issueState")
    fun setIssueState(view: ImageView, state: String){
        val drawableId = if(state == "open") {
            R.drawable.ic_issue_open
        } else {
            R.drawable.ic_issue_closed
        }
        Glide.with(view.context)
            .load(drawableId)
            .into(view)
    }

    @BindingAdapter("issueDate")
    fun setIssueDate(view: TextView, dateString: String){
        val fm = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
        val date = fm.parse(dateString)
        val today = Calendar.getInstance()
        val diffMin: Long = (today.time.time - date.time) / 60000 + 539
        val diffHour: Long = (today.time.time - date.time) / 3600000 + 9

        if(diffHour < 1){
            view.text = "${diffMin}분 전"
        } else if(diffHour < 24){
            view.text = "${diffHour}시간 전"
        } else if(diffHour < 24 * 30){
            view.text = "${diffHour / 24}일 전"
        } else if(diffHour < 24 * 30 * 12){
            view.text = "${diffHour / (24 * 30)}달 전"
        } else {
            view.text = "${diffHour / (24 * 30 * 12)}년 전"
        }
    }
}