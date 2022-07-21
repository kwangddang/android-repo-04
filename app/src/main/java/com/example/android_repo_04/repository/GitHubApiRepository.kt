package com.example.android_repo_04.repository

import com.example.android_repo_04.api.RetrofitFactory
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.utils.DataResponse

class GitHubApiRepository {
    suspend fun requestIssues(state: String, page: Int, callback: (DataResponse<List<Issue>?>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestIssues(state, page = page)
        if (response.isSuccessful) {
            callback(DataResponse.Success(data = response.body()))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }

    suspend fun requestUser(callback: (DataResponse<User?>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestUser()
        if (response.isSuccessful) {
            callback(DataResponse.Success(data = response.body()))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }

    suspend fun requestUserStarredCount(callback: (DataResponse<Int>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestUserStarred()
        if (response.isSuccessful) {
            callback(DataResponse.Success(data = response.body()!!.size))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }

    suspend fun requestNotifications(page: Int, callback: (DataResponse<List<Notification>?>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestNotifications(page)
        if (response.isSuccessful) {
            callback(DataResponse.Success(data = response.body()))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }

    suspend fun requestCommentsCount(owner: String, repo: String, callback: (DataResponse<Int>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestComments(owner, repo)
        if (response.isSuccessful) {
            callback(DataResponse.Success(data = response.body()!!.size))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }

    suspend fun requestToReadNotification(id: Long, callback: (DataResponse<String>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestToReadNotification(id)
        if (response.isSuccessful) {
            callback(DataResponse.Success())
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }

    suspend fun requestSearchRepositories(query: String, page: Int, callback: (DataResponse<Search>) -> Unit) {
        val response = RetrofitFactory.createApiService()!!.requestSearchRepositories(query, page)
        if(response.isSuccessful) {
            callback(DataResponse.Success(data = response.body()))
        } else {
            callback(DataResponse.Error(errorCode = response.code()))
        }
    }


    companion object {
        private var instance: GitHubApiRepository? = null
        fun getGitInstance(): GitHubApiRepository? {
            if (instance == null) {
                instance = GitHubApiRepository()
            }
            return instance
        }
    }
}