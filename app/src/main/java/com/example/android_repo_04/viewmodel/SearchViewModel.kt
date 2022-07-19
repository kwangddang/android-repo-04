package com.example.android_repo_04.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.utils.DataResponse
import com.example.android_repo_04.utils.debounce
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: GitHubApiRepository): ViewModel() {

    var searchText = MutableLiveData("")

    private val _searchItems = MutableLiveData<Search>()
    val searchItems: LiveData<Search> get() = _searchItems

    fun requestSearchRepositories(query: String, page: Int = 1) {
        viewModelScope.launch {
            repository.requestSearchRepositories(query,page) { response ->
                if (response is DataResponse.Success) {
                    if (page == 1) {
                        _searchItems.postValue(response.data!!)
                    } else {
                        val tempSearch = searchItems.value
                        tempSearch?.let {
                            val tempItems = it.items.toMutableList()
                            tempItems.addAll(response.data!!.items)
                            _searchItems.postValue(
                                Search(tempItems, it.total_count)
                            )
                        }
                    }
                } else if (response is DataResponse.Error) {

                }
            }
        }
    }

    fun clearItems() {
        _searchItems.postValue(Search(listOf(), 0))
    }

    fun change(time: Long, callback: () -> Unit) {
        debounce(time, viewModelScope, callback)
    }
}