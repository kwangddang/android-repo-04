package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.token.AuthToken
import retrofit2.Response

class GitHubLoginRepository {

    suspend fun requestToken(clientId: String, clientSecret: String, code: String, callback: (Response<AuthToken>) -> Unit){
        callback(RetrofitFactory.createLoginService()!!.requestToken(clientId,clientSecret,code))
    }

    companion object{
        private var instance: GitHubLoginRepository? = null
        fun getGitInstance(): GitHubLoginRepository? {
            if(instance == null){
                instance = GitHubLoginRepository()
            }
            return instance
        }
    }
}