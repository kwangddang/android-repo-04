package com.example.android_repo_04.viewmodel

import androidx.lifecycle.*
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.utils.DataResponse
import kotlinx.coroutines.launch

class MainViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    private var _position = MutableLiveData(0)
    val position: LiveData<Int> get() = _position

    private var _notifications = MutableLiveData<MutableList<Notification>>()
    val notifications: LiveData<MutableList<Notification>> get() = _notifications

    private var _readNotification = MutableLiveData<Int>()
    val readNotification: LiveData<Int> get() = _readNotification

    private val _issue = MutableLiveData<Issue>()
    val issue: LiveData<Issue> get() = _issue

    fun changePosition(pos: Int) {
        _position.postValue(pos)
    }

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

    fun requestIssues(state: String = "all", filter: String = "all"){
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
}