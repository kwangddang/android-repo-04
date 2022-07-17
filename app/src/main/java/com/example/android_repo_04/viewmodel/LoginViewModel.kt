package com.example.android_repo_04.viewmodel

import androidx.lifecycle.*
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.api.GitHubLoginRepository
import com.example.android_repo_04.utils.DataResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val gitHubLoginRepository: GitHubLoginRepository) : ViewModel() {

    private val _token = MutableLiveData("")
    val token: LiveData<String> get() = _token

    fun requestToken(code: String) {
        viewModelScope.launch {
            gitHubLoginRepository.requestToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code) { response ->
                if (response is DataResponse.Success) {
                    _token.postValue(response.data!!.accessToken)
                } else if(response is DataResponse.Error) {
                    _token.postValue(response.errorCode?.toString())
                }
            }
        }
    }
}
