package com.example.android_repo_04.data.dto.search

import com.example.android_repo_04.data.dto.Owner
import com.google.gson.annotations.SerializedName

data class SearchItem(
    val id: Long,
    val description: String?,
    val name: String,
    val language: String?,
    val owner: Owner,
    @SerializedName("stargazers_count") val stargazersCount: Int
)