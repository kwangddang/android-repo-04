package com.example.android_repo_04.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.utils.DataResponse
import kotlinx.coroutines.launch

class MainViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {
    companion object{
        private lateinit var instance: MainViewModel

        @MainThread
        fun getInstance(gitHubApiRepository: GitHubApiRepository): MainViewModel {
            instance = if(::instance.isInitialized) instance else MainViewModel(gitHubApiRepository)
            return instance
        }
    }

    val position = MutableLiveData(0)

    private var _notifications = MutableLiveData<MutableList<Notification>>()
    val notifications: LiveData<MutableList<Notification>> get() = _notifications

    private var _readNotification = MutableLiveData<Int>()
    val readNotification: LiveData<Int> get() = _readNotification

    private val _issue = MutableLiveData<Issue>()
    val issue: LiveData<Issue> get() = _issue

    val user = MutableLiveData<User>()
    val starCount = MutableLiveData<Int>()

    var selectedIssue = 0

    fun requestNotifications() {
        viewModelScope.launch {
            gitHubApiRepository.requestNotifications() { response ->
                if (response is DataResponse.Success) {
                    _notifications.postValue(response.data?.toMutableList())
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestToReadNotification(position: Int) {
        viewModelScope.launch {
            gitHubApiRepository.requestToReadNotification(notifications.value!![position].id) { response ->
                if (response is DataResponse.Success) {
                    _readNotification.postValue(position)
                } else {
                    _readNotification.postValue(-1)
                }
            }
        }
    }

    fun requestIssues(state: String = "all", filter: String = "all") {
        viewModelScope.launch {
            gitHubApiRepository.requestIssues(state, filter) { response ->
                if(response is DataResponse.Success) {
                    _issue.postValue(response.data!!)
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestUser() {
        viewModelScope.launch {
            gitHubApiRepository.requestUser() { response ->
                if(response is DataResponse.Success) {
                    user.postValue(response.data!!)
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestUserStarred() {
        viewModelScope.launch {
            gitHubApiRepository.requestUserStarredCount { response ->
                if(response is DataResponse.Success) {
                    starCount.postValue(response.data!!)
                } else {

                }
            }
        }
    }
}