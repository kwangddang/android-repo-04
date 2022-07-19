package com.example.android_repo_04.data.dto.notification

import com.example.android_repo_04.data.dto.Owner
import com.google.gson.annotations.SerializedName

data class Repository(
    val owner: Owner,
    @SerializedName("full_name") val fullName: String
)
