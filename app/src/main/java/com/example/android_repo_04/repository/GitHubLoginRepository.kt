package com.example.android_repo_04.repository

import com.example.android_repo_04.api.RetrofitFactory
import com.example.android_repo_04.data.dto.token.AuthToken
import com.example.android_repo_04.utils.DataResponse

class GitHubLoginRepository {

    suspend fun requestToken(code: String, callback: (DataResponse<AuthToken>) -> Unit){
        val response = RetrofitFactory.createLoginService()!!.requestToken(code)
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