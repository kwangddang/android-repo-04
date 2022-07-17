package com.example.android_repo_04.data.dto.issue

data class IssueItem(
    val id: Long,
    val repository: Repository,
    val state: String,
    val title: String,
    val updated_at: String
)