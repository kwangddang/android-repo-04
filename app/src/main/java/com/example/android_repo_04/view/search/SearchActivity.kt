package com.example.android_repo_04.view.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    private val searchItemsObserver: (Search) -> Unit = {
        Log.d("Test", it.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViewModel()
        observeData()
        getSearchItems()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            CustomViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[SearchViewModel::class.java]
    }

    private fun observeData() {
        viewModel.searchItems.observe(this, searchItemsObserver)
    }

    private fun getSearchItems() {
        viewModel.requestSearchRepositories("kotlin")
    }
}