package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.issue.Issue
import retrofit2.Response

class GitHubApiRepository {
    suspend fun requestIssues(token: String, state: String, filter: String, callback: (Response<Issue>) -> Unit){
        callback(RetrofitFactory.createApiService()!!.requestIssues(token,state,filter))
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