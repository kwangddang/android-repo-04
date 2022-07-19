package com.example.android_repo_04.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.api.GitHubLoginRepository

class CustomViewModelFactory(private val repository: Any?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(repository as GitHubLoginRepository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository as GitHubApiRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel() as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(repository as GitHubApiRepository) as T
        } else {
            super.create(modelClass)
        }
    }
}
