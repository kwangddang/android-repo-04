package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.token.AuthToken
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GitHubLoginService {

    @POST("access_token")
    suspend fun requestToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Header("Accept") header: String = "application/json"
    ): Response<AuthToken>
}