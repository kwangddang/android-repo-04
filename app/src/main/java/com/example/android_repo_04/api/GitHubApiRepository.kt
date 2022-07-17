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
    suspend fun requestIssues(state: String, filter: String, callback: (Issue?) -> Unit){
        val response = RetrofitFactory.createApiService()!!.requestIssues(state,filter)
        if (response.isSuccessful) {
            callback(response.body()!!)
        } else {
            callback(null)
        }
    }

    suspend fun requestUser(callback: (User?) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestUser()
        if (response.isSuccessful) {
            callback(response.body()!!)
        } else {
            callback(null)
        }
    }

    suspend fun requestUserStarredCount(callback: (Int) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestUserStarred()
        if (response.isSuccessful) {
            callback(response.body()!!.size)
        } else {
            callback(-1)
        }
    }

    suspend fun requestNotifications(callback: (List<Notification>?) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestNotifications()
        println(response)
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

    suspend fun requestToReadNotification(id: String, callback: (String) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestToReadNotification(id)
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