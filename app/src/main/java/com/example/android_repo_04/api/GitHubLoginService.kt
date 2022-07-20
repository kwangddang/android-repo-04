package com.example.android_repo_04.api

import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.data.dto.token.AuthToken
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GitHubLoginService {

    @POST(BuildConfig.TOKEN_URL)
    suspend fun requestToken(
        @Query(BuildConfig.CODE_PARAM) code: String,
        @Query(BuildConfig.CLIENT_ID_PARAM) clientId: String = BuildConfig.CLIENT_ID,
        @Query(BuildConfig.CLIENT_SECRET_PARAM) clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Header(BuildConfig.ACCEPT_HEADER) accept: String = BuildConfig.ACCEPT
    ): Response<AuthToken>
}