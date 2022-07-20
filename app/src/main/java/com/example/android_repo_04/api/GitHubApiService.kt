package com.example.android_repo_04.api

import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Comment
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.profile.Star
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.data.dto.search.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET(BuildConfig.ISSUE_URL)
    suspend fun requestIssues(
        @Query(BuildConfig.STATE_PARAM) state: String,
        @Query(BuildConfig.FILTER_PARAM) filter: String = BuildConfig.FILTER_VALUE,
        @Query(BuildConfig.PULLS_PARAM) pulls: Boolean = false,
        @Query(BuildConfig.PAGE_PARAM) page: Int
    ): Response<List<Issue>>

    @GET(BuildConfig.USER_URL)
    suspend fun requestUser(): Response<User>

    @GET(BuildConfig.USER_STARRED_URL)
    suspend fun requestUserStarred(): Response<List<Star>>

    @GET(BuildConfig.NOTIFICATIONS_URL)
    suspend fun requestNotifications(
        @Query(BuildConfig.PAGE_PARAM) page: Int
    ): Response<List<Notification>>

    @GET(BuildConfig.COMMENTS_URL)
    suspend fun requestComments(
        @Path(BuildConfig.OWNER_PATH) owner: String,
        @Path(BuildConfig.REPO_PATH) repo: String,
    ): Response<List<Comment>>

    @PATCH(BuildConfig.NOTIFICATIONS_READ_URL)
    suspend fun requestToReadNotification(
        @Path(BuildConfig.ID_PATH) id: String,
    ): Response<String>

    @GET(BuildConfig.SEARCH_URL)
    suspend fun requestSearchRepositories(
        @Query(BuildConfig.QUERY_PARAM) query: String,
        @Query(BuildConfig.PAGE_PARAM) page: Int
    ): Response<Search>
}