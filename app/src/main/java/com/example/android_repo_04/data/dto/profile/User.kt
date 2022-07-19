package com.example.android_repo_04.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String,
    val name: String,
    val location: String,
    val bio: String,
    val blog: String,
    val email: String,
    val followers: Int,
    val following: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("public_repos") val publicRepos: Int,
    @SerializedName("total_private_repos") val totalPrivateRepos: Int,
): Parcelable