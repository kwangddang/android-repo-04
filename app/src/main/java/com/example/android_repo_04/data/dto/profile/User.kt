package com.example.android_repo_04.data.dto.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatar_url: String,
    val bio: String,
    val blog: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val location: String,
    val login: String,
    val name: String,
    val public_repos: Int,
    val total_private_repos: Int,
): Parcelable