package com.example.android_repo_04.data.dto

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("login") val login: String
)