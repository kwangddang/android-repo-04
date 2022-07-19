package com.example.android_repo_04.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.utils.DataResponse
import com.example.android_repo_04.view.Event
import com.example.android_repo_04.view.emit
import kotlinx.coroutines.launch

class ProfileViewModel(): ViewModel() {
    val user = MutableLiveData<User>()
    val star = MutableLiveData<Int>()
    private val _clickEvent = MutableLiveData<Event<Unit>>()
    val clickEvent: LiveData<Event<Unit>> get() = _clickEvent

    fun clickEvent() {
        _clickEvent.emit()
    }
}