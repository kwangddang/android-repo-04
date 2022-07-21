package com.example.android_repo_04.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_04.repository.GitHubApiRepository
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.utils.*
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: GitHubApiRepository): ViewModel() {

    var searchText = MutableLiveData("")

    private val _searchItems = MutableLiveData<Search>()
    val searchItems: LiveData<Search> get() = _searchItems

    private val _clickEvent = MutableLiveData<Event<Int>>()
    val clickEvent: LiveData<Event<Int>> get() = _clickEvent

    private val _refreshEvent = MutableLiveData<Event<Unit>>()
    val refreshEvent: LiveData<Event<Unit>> get() = _refreshEvent

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
                                Search(tempItems, it.totalCount)
                            )
                        }
                    }
                } else if (response is DataResponse.Error) {
                    createErrorToast(response.errorCode)
                    clearItems()
                }
            }
        }
    }

    fun debounce(time: Long, callback: () -> Unit) {
        debounce(time, viewModelScope, callback)
    }

    fun clearItems() {
        _searchItems.postValue(Search(listOf(), 0))
    }

    fun setClickEvent(view: View) {
        _clickEvent.emit(view.id)
    }

    fun setRefreshEvent() {
        _refreshEvent.emit()
    }
}