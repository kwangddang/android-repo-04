package com.example.android_repo_04.utils

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

fun pxToDp(context: Context, px: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (px * scale + 0.5f).toInt()
}