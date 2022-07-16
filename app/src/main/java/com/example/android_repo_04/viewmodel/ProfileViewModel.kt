package com.example.android_repo_04.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.profile.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    val user = MutableLiveData<User>()
    val star = MutableLiveData<Int>()

    fun requestUser(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestUser(token) { response ->
                if(response.isSuccessful) {
                    user.postValue(response.body())
                }
            }
        }
    }

    fun requestUserStarred(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestUserStarred(token) { response ->
                if(response.isSuccessful) {
                    star.postValue(response.body()!!.size)
                }
            }
        }
    }
}

class ProfileViewModelFactory(private val gitHubApiRepository: GitHubApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(gitHubApiRepository) as T
        }
        throw IllegalAccessException()
    }
}