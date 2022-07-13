package com.example.android_repo_04.data

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id") val id: String,
    @SerializedName("unread") val unread: Boolean
)
