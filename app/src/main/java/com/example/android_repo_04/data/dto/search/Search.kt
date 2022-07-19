package com.example.android_repo_04.data.dto.search

import com.google.gson.annotations.SerializedName

data class Search(
    val items: List<SearchItem>,
    @SerializedName("total_count") val totalCount: Int
)