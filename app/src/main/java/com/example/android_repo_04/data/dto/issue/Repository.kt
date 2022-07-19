package com.example.android_repo_04.data.dto.issue

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("full_name") val fullName: String,
)