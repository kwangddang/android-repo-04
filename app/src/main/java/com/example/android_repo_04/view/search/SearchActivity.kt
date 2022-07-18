package com.example.android_repo_04.view.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.databinding.ActivitySearchBinding
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding

    private val searchItemsObserver: (Search) -> Unit = { search ->
        searchAdapter.replaceItem(search)
    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initAdapter()
        observeData()
        getSearchItems()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            CustomViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[SearchViewModel::class.java]
    }

    private fun initAdapter() {
        binding.recyclerSearch.adapter = searchAdapter
    }

    private fun observeData() {
        viewModel.searchItems.observe(this, searchItemsObserver)
    }

    private fun getSearchItems() {
        viewModel.requestSearchRepositories("kotlin")
    }
}