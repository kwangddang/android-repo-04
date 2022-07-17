package com.example.android_repo_04.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.profile.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    val user = MutableLiveData<User>()
    val star = MutableLiveData<Int>()

    fun requestUser() {
        viewModelScope.launch {
            gitHubApiRepository.requestUser() {
                if(it != null) {
                    user.postValue(it)
                } else {
                    //TODO 에러처리
                }
            }
        }
    }

    fun requestUserStarred() {
        viewModelScope.launch {
            gitHubApiRepository.requestUserStarredCount() {
                if(it != -1) {
                    star.postValue(it)
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