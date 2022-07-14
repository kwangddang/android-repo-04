package com.example.android_repo_04.data.dto.notificaiton

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id") val id: String,
    @SerializedName("repository") val repository: Repository,
    @SerializedName("unread") val unread: Boolean
)
