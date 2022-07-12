package com.example.android_repo_04.api

import com.example.android_repo_04.data.AuthToken
import retrofit2.Response
import retrofit2.http.Query

class GitHubRepository {

    suspend fun requestToken(clientId: String, clientSecret: String, code: String, callback: (Response<AuthToken>) -> Unit){
        callback(RetrofitFactory.create().requestToken(clientId,clientSecret,code))
    }
}