package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.utils.Event
import com.example.android_repo_04.utils.emit

class ProfileViewModel(): ViewModel() {
    val user = MutableLiveData<User>()
    val star = MutableLiveData<Int>()
    private val _clickEvent = MutableLiveData<Event<Unit>>()
    val clickEvent: LiveData<Event<Unit>> get() = _clickEvent

    fun setClickEvent() {
        _clickEvent.emit()
    }
}