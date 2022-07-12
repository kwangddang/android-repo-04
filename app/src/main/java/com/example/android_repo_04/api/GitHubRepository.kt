package com.example.android_repo_04.api

import com.example.android_repo_04.data.AuthToken
import retrofit2.Response

class GitHubRepository {

    suspend fun requestToken(clientId: String, clientSecret: String, code: String, callback: (Response<AuthToken>) -> Unit){
        callback(RetrofitFactory.createLoginService()!!.requestToken(clientId,clientSecret,code))
    }

    companion object{
        private var instance: GitHubRepository? = null
        fun getGitInstance(): GitHubRepository? {
            if(instance == null){
                instance = GitHubRepository()
            }
            return instance
        }
    }
}