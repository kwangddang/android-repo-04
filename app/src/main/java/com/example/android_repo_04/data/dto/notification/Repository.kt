package com.example.android_repo_04.data.dto.notification

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("full_name") val full_name: String,
    @SerializedName("owner") val owner: Owner
)
