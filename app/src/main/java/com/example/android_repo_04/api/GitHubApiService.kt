package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.profile.Star
import com.example.android_repo_04.data.dto.profile.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GitHubApiService {
    @GET("issues")
    suspend fun requestIssues(
        @Header("Authorization") token: String,
        @Query("state") state: String,
        @Query("filter") filter: String
    ): Response<Issue>

    @GET("user")
    suspend fun requestUser(
        @Header("Authorization") token: String
    ): Response<User>

    @GET("user/starred")
    suspend fun requestUserStarred(
        @Header("Authorization") token: String
    ): Response<List<Star>>
}