package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.api.GitHubLoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val gitHubLoginRepository: GitHubLoginRepository) : ViewModel() {

    private val _token = MutableLiveData("")
    val token: LiveData<String> get() = _token

    fun requestToken(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubLoginRepository.requestToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code) { response ->
                if (response.isSuccessful && response.body()?.accessToken != null) {
                    _token.postValue(response.body()?.accessToken)
                } else {
                    _token.postValue("error")
                }
            }
        }
    }
}

class LoginViewModelFactory(private val gitHubRepository: GitHubLoginRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(gitHubRepository) as T
        }
        throw IllegalAccessException()
    }
}
