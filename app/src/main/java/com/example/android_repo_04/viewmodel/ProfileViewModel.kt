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

class ProfileViewModel(): ViewModel() {
    val user = MutableLiveData<User>()
    val star = MutableLiveData<Int>()
}