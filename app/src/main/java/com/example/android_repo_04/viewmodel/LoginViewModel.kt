package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.api.GitHubLoginRepository
import com.example.android_repo_04.utils.*
import kotlinx.coroutines.launch

class LoginViewModel(private val gitHubLoginRepository: GitHubLoginRepository) : ViewModel() {

    private val _token = MutableLiveData("")
    val token: LiveData<String> get() = _token

    private val _clickEvent = MutableLiveData<Event<Unit>>()
    val clickEvent: LiveData<Event<Unit>> get() = _clickEvent

    fun requestToken(code: String) {
        viewModelScope.launch {
            gitHubLoginRepository.requestToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code) { response ->
                if (response is DataResponse.Success) {
                    _token.postValue(response.data!!.accessToken)
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
