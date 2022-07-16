package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private var _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications

    private val _issue = MutableLiveData<Issue>()
    val issue: LiveData<Issue> get() = _issue

    fun changePosition(pos: Int) {
        _position.postValue(pos)
    }

    fun requestNotifications(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestNotifications(token) {
                if (it.isSuccessful) {
                    _notifications.postValue(it.body()!!.toMutableList())
                } else {

                }
            }
        }
    }

    fun requestToReadNotification(position: Int, token: String, completed: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            gitHubApiRepository.requestToReadNotification(
                _notifications.value!![position].id,
                token
            ) {
                if (it.isSuccessful) {
                    completed()
                } else {
                }
            }
        }
    }

    fun requestIssues(token: String, state: String = "all", filter: String = "all"){
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestIssues(token, state, filter){ response ->
                if(response.isSuccessful){
                    _issue.postValue(response.body())
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