package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.issue.Issue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GitHubApiService {
    @GET("issues")
    suspend fun getIssues(
        @Header("Authorization") token: String,
        @Query("state") state: String,
        @Query("filter") filter: String
    ): Response<Issue>
}