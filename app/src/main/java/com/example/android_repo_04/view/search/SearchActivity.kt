package com.example.android_repo_04.view.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.data.dto.search.Search
import com.example.android_repo_04.databinding.ActivitySearchBinding
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity(), SearchRefreshListener {

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
    }

    private val editorActionListener: (TextView, Int, KeyEvent?) -> Boolean = { view, actionId, event ->
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            showProgress()
            getSearchItems(view.text.toString())
            true
        }
        false
    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(viewModel)
    }

    private val imgSearchClickListener: (View) -> Unit = {
        getSearchItems(binding.editSearchSearch.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBinding()
        initAdapter()
        setOnClickListeners()
        setRefreshListener()
        setOnEditorActionListener()
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

    private fun setOnClickListeners() {
        binding.imgSearchSearch.setOnClickListener(imgSearchClickListener)
        binding.imgSearchBack.setOnClickListener { onBackPressed() }
        binding.imgSearchCancel.setOnClickListener { binding.editSearchSearch.setText("") }
    }

    private fun setRefreshListener() {
        binding.refreshSearch.setOnRefreshListener {
            getSearchItems(binding.editSearchSearch.text.toString())
        }
    }

    private fun setOnEditorActionListener() {
        binding.editSearchSearch.setOnEditorActionListener(editorActionListener)
    }

    private fun setOnScrollListener() {
        binding.recyclerSearch.addOnScrollListener(
            SearchScrollListener(viewModel, this)
        )
    }

    private fun observeData() {
        viewModel.searchItems.observe(this, searchItemsObserver)
        viewModel.searchText.observe(this, searchTextObserver)
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