package com.example.android_repo_04.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
