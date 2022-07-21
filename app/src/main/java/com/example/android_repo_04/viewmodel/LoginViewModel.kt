package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.api.GitHubLoginRepository
import com.example.android_repo_04.utils.DataResponse
import com.example.android_repo_04.utils.Event
import com.example.android_repo_04.utils.createTokenErrorToast
import com.example.android_repo_04.utils.emit
import kotlinx.coroutines.launch

class LoginViewModel(private val gitHubLoginRepository: GitHubLoginRepository) : ViewModel() {

    private val _tokenEvent = MutableLiveData<Event<String>>()
    val tokenEvent: MutableLiveData<Event<String>> get() = _tokenEvent

    private val _clickEvent = MutableLiveData<Event<Unit>>()
    val clickEvent: LiveData<Event<Unit>> get() = _clickEvent

    fun requestToken(code: String) {
        viewModelScope.launch {
            gitHubLoginRepository.requestToken(code) { response ->
                if (response is DataResponse.Success) {
                    _tokenEvent.postValue(Event(response.data!!.accessToken))
                } else if(response is DataResponse.Error) {
                    createTokenErrorToast()
                }
            }
        }
    }

    fun setClickEvent() {
        _clickEvent.emit()
    }
}
