package com.example.android_repo_04.api

import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Comment
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.profile.Star
import com.example.android_repo_04.data.dto.profile.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

class GitHubApiRepository {
    suspend fun requestIssues(token: String, state: String, filter: String, callback: (Issue?) -> Unit){
        val response = RetrofitFactory.createApiService()!!.requestIssues(token,state,filter)
        if (response.isSuccessful) {
            callback(response.body()!!)
        } else {
            callback(null)
        }
    }

    suspend fun requestUser(token: String, callback: (User?) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestUser(token)
        if (response.isSuccessful) {
            callback(response.body()!!)
        } else {
            callback(null)
        }
    }

    suspend fun requestUserStarredCount(token: String, callback: (Int) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestUserStarred(token)
        if (response.isSuccessful) {
            callback(response.body()!!.size)
        } else {
            callback(-1)
        }
    }

    suspend fun requestNotifications(token: String, callback: (List<Notification>?) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestNotifications(token = token)
        if (response.isSuccessful) {
            callback(response.body()!!)
        } else {
            callback(null)
        }
    }

    suspend fun requestCommentsCount(owner: String, repo: String, callback: (Int) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestComments(owner, repo)
        if (response.isSuccessful) {
            callback(response.body()!!.size)
        } else {
            callback(-1)
        }
    }

    suspend fun requestToReadNotification(id: String, token: String, callback: (String) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestToReadNotification(id, token = token)
        if (response.isSuccessful) {
            callback("success")
        } else {
            callback("error")
        }
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