package com.example.android_repo_04.view.main.issue

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.pxToDp

class SpinnerAdapter(private val context: Context) : BaseAdapter(){

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val list = listOf("Open", "Closed", "All")

    var selectedPosition = 0

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(convertView == null) {
            val view = inflater.inflate(R.layout.spinner_title, parent, false)
            view.findViewById<TextView>(R.id.text_spinner_title).text = list[position]
            return view
        } else {
            convertView.findViewById<TextView>(R.id.text_spinner_title).text = list[position]
        }
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var contentView: CheckedTextView
        if(convertView == null) {
            val view = inflater.inflate(R.layout.spinner_content, parent, false)
            contentView = view.findViewById(R.id.text_spinner_content)
            contentView.text = list[position]
            setChecked(position, contentView)
            return view
        } else {
            contentView = convertView.findViewById(R.id.text_spinner_content)
            contentView.text = list[position]
        }
        if(position == 2) setLastItemPadding(contentView)
        setChecked(position, contentView)
        return convertView
    }

    private fun setLastItemPadding(contentView: CheckedTextView) {
        contentView.apply { setPadding(pxToDp(context, 20), pxToDp(context, 16), pxToDp(context, 20), pxToDp(context, 16)) }
    }

    private fun setChecked(position: Int, view: CheckedTextView) {
        if(position == selectedPosition) {
            view.isChecked = true
            view.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            view.isChecked = false
            view.setTextColor(ContextCompat.getColor(context, R.color.grey))
        }
    }
}