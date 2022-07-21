package com.example.android_repo_04.data.dto.notification

import com.google.gson.annotations.SerializedName

data class Notification(
    val id: Long,
    val repository: Repository,
    val subject: Subject,
    val unread: Boolean,
    @SerializedName("updated_at") val updatedAt: String
)
