package com.example.android_repo_04.api

import android.os.Build
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Comment
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.profile.Star
import com.example.android_repo_04.data.dto.profile.User
import retrofit2.Response
import retrofit2.http.*

interface GitHubApiService {
    @GET(BuildConfig.ISSUE_URL)
    suspend fun requestIssues(
        @Query("state") state: String,
        @Query("filter") filter: String
    ): Response<Issue>

    @GET(BuildConfig.USER_URL)
    suspend fun requestUser(): Response<User>

    @GET(BuildConfig.USER_STARRED_URL)
    suspend fun requestUserStarred(): Response<List<Star>>

    @GET(BuildConfig.NOTIFICATIONS_URL)
    suspend fun requestNotifications(): Response<List<Notification>>

    @GET(BuildConfig.COMMENTS_URL)
    suspend fun requestComments(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Response<List<Comment>>

    @PATCH(BuildConfig.NOTIFICATIONS_READ_URL)
    suspend fun requestToReadNotification(
        @Path("id") id: String,
    ): Response<String>
}