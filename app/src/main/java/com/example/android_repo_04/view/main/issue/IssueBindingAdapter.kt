package com.example.android_repo_04.view.main.issue

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.dateToFormattedString

@BindingAdapter("issueState")
fun ImageView.setIssueState(state: String) {
    val drawableId = if (state == context.getString(R.string.state_open)) {
        R.drawable.ic_issue_open
    } else {
        R.drawable.ic_issue_closed
    }
    setImageResource(drawableId)
}

@BindingAdapter("issueDate")
fun TextView.setIssueDate(dateString: String) {
    text = dateToFormattedString(dateString)
}

@BindingAdapter("selectedSpinnerItem")
fun Spinner.setSelectedSpinnerItem(selectedValue: Int?) {

}

@BindingAdapter("selectedSpinnerItemAttrChanged")
fun Spinner.setSelectedSpinnerItemInverseBindingListener(listener: InverseBindingListener) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            (adapter as SpinnerAdapter).selectedPosition = position
            listener.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}

@InverseBindingAdapter(attribute = "selectedSpinnerItem", event = "selectedSpinnerItemAttrChanged")
fun Spinner.getSelectedSpinnerItem(): Int {
    return selectedItemPosition
}
