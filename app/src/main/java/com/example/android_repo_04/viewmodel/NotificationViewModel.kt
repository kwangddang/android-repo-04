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

    private val _notification = ListLiveData<Notification>()
    val notification: ListLiveData<Notification> get() = _notification

    private val _removed = MutableLiveData(-2)
    val removed: MutableLiveData<Int> get() = _removed

    fun requestNotifications(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestNotifications(token) {
                if (it.isSuccessful) {
                    _notification.clear()
                    _notification.addAll(it.body()!!.toMutableList())
                } else {

                }
            }
        }
    }

    fun removeNotification(position: Int, token: String) {
        if (token == "") {
            _removed.postValue(-1)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestToReadNotification(
                notification.value!![position].id,
                token
            ) {
                if (it.isSuccessful) {
                    _removed.postValue(position)
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

class ListLiveData<T>: MutableLiveData<MutableList<T>>() {
    private val temp = mutableListOf<T>()

    init {
        postValue(temp)
    }

    fun add(item: T) {
        temp.add(item)
        postValue(temp)
    }

    fun addAll(items: List<T>) {
        temp.addAll(items)
        postValue(temp)
    }

    fun remove(item: T) {
        temp.remove(item)
        postValue(temp)
    }

    fun clear() {
        temp.clear()
        postValue(temp)
    }
}