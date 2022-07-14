package com.example.android_repo_04.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueViewModel(private val gitHubApiRepository: GitHubApiRepository): ViewModel() {

    private val _issue = MutableLiveData<Issue>()
    val issue: LiveData<Issue> get() = _issue

    fun requestIssues(token: String, state: String = "all", filter: String = "all"){
        CoroutineScope(Dispatchers.IO).launch {
            gitHubApiRepository.requestIssues(token, state, filter){ response ->
                if(response.isSuccessful){
                    _issue.postValue(response.body())
                } else {
                    //TODO 에러처리
                }
            }
        }
    }
}

class IssueViewModelFactory(private val gitHubApiRepository: GitHubApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssueViewModel::class.java)) {
            return IssueViewModel(gitHubApiRepository) as T
        }
        throw IllegalAccessException()
    }
}