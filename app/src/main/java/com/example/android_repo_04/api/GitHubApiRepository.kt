package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.profile.Star
import com.example.android_repo_04.data.dto.profile.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

class GitHubApiRepository {
    suspend fun requestIssues(token: String, state: String, filter: String, callback: (Response<Issue>) -> Unit){
        callback(RetrofitFactory.createApiService()!!.requestIssues(token,state,filter))
    }

    suspend fun requestUser(token: String, callback: (Response<User>) -> Unit) {
        callback(RetrofitFactory.createApiService()!!.requestUser(token))
    }

    suspend fun requestUserStarred(token: String, callback: (Response<List<Star>>) -> Unit) {
        callback(RetrofitFactory.createApiService()!!.requestUserStarred(token))
    }

    companion object{
        private var instance: GitHubApiRepository? = null
        fun getGitInstance(): GitHubApiRepository? {
            if(instance == null){
                instance = GitHubApiRepository()
            }
            return instance
        }
    }
}