package com.example.android_repo_04.data

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("token_type") val tokenType: String
)
