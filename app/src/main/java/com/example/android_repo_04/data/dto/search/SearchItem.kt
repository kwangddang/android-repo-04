package com.example.android_repo_04.data.dto.search

import com.example.android_repo_04.data.dto.Owner

data class SearchItem(
    val description: String?,
    val name: String,
    val language: String?,
    val owner: Owner,
    val stargazers_count: Int
)