package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.notification.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    private val _notification = MutableLiveData<List<Notification>>()
    val notification: LiveData<List<Notification>> get() = _notification

    fun requestNotifications(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestNotifications(token) {
                if (it.isSuccessful) {
                    _notification.postValue(it.body())
                } else {

                }
            }
        }
    }
}

class NotificationViewModelFactory(private val gitHubApiRepository: GitHubApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(gitHubApiRepository) as T
        }
        throw IllegalAccessException()
    }
}