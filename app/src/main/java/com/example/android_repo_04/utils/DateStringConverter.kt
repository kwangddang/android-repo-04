package com.example.android_repo_04.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateToFormattedString(str: String): String {
    val fm = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
    val date = fm.parse(str)
    val today = Calendar.getInstance()
    val diffMin: Long = (today.time.time - date.time) / 60000
    val diffHour: Long = (today.time.time - date.time) / 3600000

    return if(diffHour < 1){
        "${diffMin}분 전"
    } else if(diffHour < 24){
        "${diffHour}시간 전"
    } else if(diffHour < 24 * 30){
        "${diffHour / 24}일 전"
    } else if(diffHour < 24 * 30 * 12){
        "${diffHour / (24 * 30)}달 전"
    } else {
        "${diffHour / (24 * 30 * 12)}년 전"
    }
}
