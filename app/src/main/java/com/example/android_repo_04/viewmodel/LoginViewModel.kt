package com.example.android_repo_04.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.api.GitHubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val gitHubRepository: GitHubRepository) : ViewModel() {

    private val _token = MutableLiveData("")
    val token: LiveData<String> get() = _token

    fun requestToken(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubRepository.requestToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code) { response ->
                if (response.isSuccessful && response.body()?.accessToken != null) {
                    _token.postValue(response.body()?.accessToken)
                } else {
                    _token.postValue("error")
                }
            }
        }
    }
}

class LoginViewModelFactory(private val gitHubRepository: GitHubRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(gitHubRepository) as T
        }
        throw IllegalAccessException()
    }
}
