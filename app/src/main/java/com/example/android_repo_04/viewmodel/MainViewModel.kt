package com.example.android_repo_04.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.notification.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    private var _position = MutableLiveData(0)
    val position: LiveData<Int> get() = _position

    private var _notifications = MutableLiveData<MutableList<Notification>>()
    val notifications: LiveData<MutableList<Notification>> get() = _notifications

    private val _issue = MutableLiveData<Issue>()
    val issue: LiveData<Issue> get() = _issue

    fun changePosition(pos: Int) {
        _position.postValue(pos)
    }

    fun requestNotifications(token: String) {
        viewModelScope.launch {
            gitHubApiRepository.requestNotifications(token) {
                if (it != null) {
                    _notifications.postValue(it.toMutableList())
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestToReadNotification(position: Int, token: String) {
        viewModelScope.launch {
            gitHubApiRepository.requestToReadNotification(
                _notifications.value!![position].id,
                token
            ) {
                if (it == "success") {
                    //TODO 성공처리
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestIssues(token: String, state: String = "all", filter: String = "all"){
        viewModelScope.launch {
            gitHubApiRepository.requestIssues(token, state, filter) {
                if (it != null){
                    _issue.postValue(it)
                } else {
                    //TODO 에러처리
                }
            }
        }
    }
}

class MainViewModelFactory(private val gitHubApiRepository: GitHubApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(gitHubApiRepository) as T
        }
        throw IllegalAccessException()
    }
}