package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.utils.DataResponse
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: GitHubApiRepository): ViewModel() {
    private val _searchItems = MutableLiveData<Search>()
    val searchItems: LiveData<Search> get() = _searchItems

    fun requestSearchRepositories(query: String, page: Int = 1) {
        viewModelScope.launch {
            repository.requestSearchRepositories(query,page) { response ->
                if(response is DataResponse.Success) {
                    _searchItems.postValue(response.data!!)
                } else if(response is DataResponse.Error) {

                }
            }
        }
    }
}