package com.example.android_repo_04.view.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.databinding.ActivitySearchBinding
import com.example.android_repo_04.utils.EventObserver
import com.example.android_repo_04.view.listener.RefreshListener
import com.example.android_repo_04.view.listener.ScrollListener
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity(), RefreshListener {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding

    private val searchItemsObserver: (Search) -> Unit = { search ->
        binding.progressSearchLoading.visibility = View.INVISIBLE
        searchAdapter.replaceItem(search.items)
        binding.refreshSearch.isRefreshing = false
    }

    private val searchTextObserver: (String) -> Unit = { text ->
        if (text.isNotEmpty()) {
            showItems()
        } else {
            viewModel.clearItems()
            binding.progressSearchLoading.visibility = View.INVISIBLE
            showHintText()
        }
        viewModel.change(500L) {
            if (text.isNotEmpty() && text.isNotBlank()) {
                showProgress()
                getSearchItems(text)
            }
        }
    }

    private val clickEventObserver: (Int) -> Unit = { event ->
        when(event) {
            R.id.img_search_back -> onBackPressed()
            R.id.img_search_cancel -> binding.editSearchSearch.setText("")
        }
    }

    private val refreshEventObserver: (Unit) -> Unit = { event ->
        binding.editSearchSearch.run {
            if(this.text.isEmpty() || this.text.isBlank()) {
                binding.refreshSearch.isRefreshing = false
            } else {
                getSearchItems(this.text.toString())
            }
        }
    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBinding()
        initAdapter()
        setOnScrollListener()
        observeData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            CustomViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[SearchViewModel::class.java]
    }

    private fun initBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initAdapter() {
        binding.recyclerSearch.adapter = searchAdapter
    }

    private fun setOnScrollListener() {
        binding.recyclerSearch.addOnScrollListener(ScrollListener(this) { nextPage ->
            viewModel.requestSearchRepositories(
                viewModel.searchText.value.toString(),
                nextPage
            )
        })
    }

    private fun observeData() {
        viewModel.searchItems.observe(this, searchItemsObserver)
        viewModel.searchText.observe(this, searchTextObserver)
        viewModel.clickEvent.observe(this, EventObserver(clickEventObserver))
        viewModel.refreshEvent.observe(this, EventObserver(refreshEventObserver))
    }

    private fun getSearchItems(query: String) {
        viewModel.requestSearchRepositories(query)
    }

    private fun showHintText() {
        binding.apply {
            layoutSearchInnerContainer.setBackgroundResource(R.drawable.background_round_navy)
            imgSearchCancel.visibility = View.GONE
            imgSearchSearch.visibility = View.VISIBLE
            recyclerSearch.visibility = View.INVISIBLE
            textSearchEmptyTitle.visibility = View.VISIBLE
            textSearchEmptyDescription.visibility = View.VISIBLE
        }
    }

    private fun showItems() {
        binding.apply {
            layoutSearchInnerContainer.setBackgroundResource(R.drawable.background_round_navy_selected)
            imgSearchCancel.visibility = View.VISIBLE
            imgSearchSearch.visibility = View.GONE
            recyclerSearch.visibility = View.VISIBLE
            textSearchEmptyTitle.visibility = View.INVISIBLE
            textSearchEmptyDescription.visibility = View.INVISIBLE
        }
    }

    override fun showProgress() {
        binding.progressSearchLoading.visibility = View.VISIBLE
    }
}