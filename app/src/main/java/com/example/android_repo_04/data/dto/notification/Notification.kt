package com.example.android_repo_04.data.dto.notification

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id") val id: String,
    @SerializedName("repository") val repository: Repository,
    @SerializedName("subject") val subject: Subject,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("unread") val unread: Boolean
)
