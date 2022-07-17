package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.token.AuthToken
import com.example.android_repo_04.utils.DataResponse

class GitHubLoginRepository {

    suspend fun requestToken(clientId: String, clientSecret: String, code: String, callback: (DataResponse<AuthToken>) -> Unit){
        val response = RetrofitFactory.createLoginService()!!.requestToken(clientId, clientSecret, code)
        if (response.isSuccessful && response.body()?.accessToken != null) {
            callback(DataResponse.Success(data = response.body()))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
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