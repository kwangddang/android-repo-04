package com.example.android_repo_04.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.utils.DataResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    val user = MutableLiveData<User>()
    val star = MutableLiveData<Int>()

    fun requestUser() {
        viewModelScope.launch {
            gitHubApiRepository.requestUser() { response ->
                if(response is DataResponse.Success) {
                    user.postValue(response.data!!)
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestUserStarred() {
        viewModelScope.launch {
            gitHubApiRepository.requestUserStarredCount { response ->
                if(response is DataResponse.Success) {
                    star.postValue(response.data!!)
                } else {
                    //TODO 에러처리
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