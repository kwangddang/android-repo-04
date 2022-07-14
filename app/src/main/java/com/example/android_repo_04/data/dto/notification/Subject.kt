package com.example.android_repo_04.data.dto.notification

import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("title") val title: String
)