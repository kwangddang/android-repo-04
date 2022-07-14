package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Notification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("issues")
    suspend fun requestIssues(
        @Header("Authorization") token: String,
        @Query("state") state: String,
        @Query("filter") filter: String
    ): Response<Issue>

    @GET("notifications")
    suspend fun requestNotifications(
        @Header("Accept") header: String = "application/json",
        @Header("Authorization") token: String
    ): Response<List<Notification>>

    @GET("repos/{owner}/{repo}/comments")
    suspend fun requestComments(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Header("Accept") header: String = "application/json"
    ): Response<List<String>>
}