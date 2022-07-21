package com.example.android_repo_04.data.dto.issue

import com.google.gson.annotations.SerializedName

data class Issue(
    val id: Long,
    val repository: Repository,
    val state: String,
    val title: String,
    @SerializedName("updated_at") val updatedAt: String
)