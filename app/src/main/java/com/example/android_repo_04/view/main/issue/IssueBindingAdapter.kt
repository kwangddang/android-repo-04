package com.example.android_repo_04.view.main.issue

import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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

    @JvmStatic
    @BindingAdapter("selectedSpinnerItem")
    fun setSelectedSpinnerItem(view: Spinner, selectedValue: Int?) {

    }

    @JvmStatic
    @BindingAdapter("selectedSpinnerItemAttrChanged")
    fun setSelectedSpinnerItemInverseBindingListener(spinner: Spinner, listener: InverseBindingListener) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (spinner.adapter as SpinnerAdapter).selectedPosition = position
                listener.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedSpinnerItem", event = "selectedSpinnerItemAttrChanged")
    fun getSelectedSpinnerItem(view: Spinner): Int {
        return view.selectedItemPosition
    }
}