package com.example.android_repo_04.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.api.GitHubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val token = MutableLiveData("")

    fun requestToken(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            GitHubRepository().requestToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code) { reponse ->
                token.postValue(reponse.body()?.accessToken)
            }
        }
    }
}

class LoginViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}
