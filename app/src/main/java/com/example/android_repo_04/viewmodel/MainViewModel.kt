package com.example.android_repo_04.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.utils.*
import kotlinx.coroutines.launch

class MainViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    val position = MutableLiveData(0)

    private var _notifications = MutableLiveData<MutableList<Notification>>()
    val notifications: LiveData<MutableList<Notification>> get() = _notifications

    private val _issue = MutableLiveData<MutableList<Issue>>()
    val issue: LiveData<MutableList<Issue>> get() = _issue

    private val _mainClickEvent = MutableLiveData<Event<Int>>()
    val mainClickEvent: LiveData<Event<Int>> get() = _mainClickEvent

    private val _notificationRefreshEvent = MutableLiveData<Event<Unit>>()
    val notificationRefreshEvent: LiveData<Event<Unit>> get() = _notificationRefreshEvent

    private val _issueRefreshEvent = MutableLiveData<Event<Unit>>()
    val issueRefreshEvent: LiveData<Event<Unit>> get() = _issueRefreshEvent

    private val _issueClickEvent = MutableLiveData<Event<Unit>>()
    val issueClickEvent: LiveData<Event<Unit>> get() = _issueClickEvent

    val user = MutableLiveData<User>()
    val starCount = MutableLiveData<Int>()

    var selectedIssue = MutableLiveData(0)
    var prevSelectedIssue = 0

    fun requestNotifications(page: Int = 1) {
        viewModelScope.launch {
            gitHubApiRepository.requestNotifications(page) { response ->
                if (response is DataResponse.Success) {
                    if (page == 1) {
                        _notifications.postValue(response.data?.toMutableList())
                    } else {
                        val tempNotifications = notifications.value
                        tempNotifications?.let {
                            val tempItems = it.toMutableList()
                            tempItems.addAll(response.data!!)
                            _notifications.postValue(tempItems)
                        }
                    }
                } else if (response is DataResponse.Error) {
                    createErrorToast(response.errorCode)
                }
            }
        }
    }

    fun requestToReadNotification(position: Int) {
        viewModelScope.launch {
            gitHubApiRepository.requestToReadNotification(notifications.value!![position].id) { response ->
                if (response is DataResponse.Success) {
                    val temp = _notifications.value!!
                    temp.removeAt(position)
                    _notifications.postValue(temp)
                } else if (response is DataResponse.Error) {
                    createErrorToast(response.errorCode)
                    _notifications.postValue(_notifications.value!!)
                }
            }
        }
    }

    fun requestIssues(state: String, page: Int = 1) {
        viewModelScope.launch {
            gitHubApiRepository.requestIssues(state, page) { response ->
                if(response is DataResponse.Success) {
                    if (page == 1) {
                        _issue.postValue(response.data?.toMutableList())
                    } else {
                        val tempIssues = issue.value
                        tempIssues?.let {
                            val tempItems = it.toMutableList()
                            tempItems.addAll(response.data!!)
                            _issue.postValue(tempItems)
                        }
                    }
                } else if (response is DataResponse.Error) {
                    createErrorToast(response.errorCode)
                }
            }
        }
    }

    fun requestUser() {
        viewModelScope.launch {
            gitHubApiRepository.requestUser() { response ->
                if(response is DataResponse.Success) {
                    user.postValue(response.data!!)
                } else if (response is DataResponse.Error) {
                    createErrorToast(response.errorCode)
                }
            }
        }
    }

    fun requestUserStarred() {
        viewModelScope.launch {
            gitHubApiRepository.requestUserStarredCount { response ->
                if(response is DataResponse.Success) {
                    starCount.postValue(response.data!!)
                } else if (response is DataResponse.Error) {
                    createErrorToast(response.errorCode)
                }
            }
        }
    }

    fun setMainClickEvent(view: View) {
        _mainClickEvent.emit(view.id)
    }

    fun setNotificationRefreshEvent() {
        _notificationRefreshEvent.emit()
    }

    fun setIssueRefreshEvent() {
        _issueRefreshEvent.emit()
    }

    fun setIssueClickEvent() {
        _issueClickEvent.emit()
    }
}