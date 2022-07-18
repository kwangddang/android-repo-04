package com.example.android_repo_04.view.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.viewmodel.SearchViewModel

class SearchScrollListener(
    private val viewModel: SearchViewModel,
    private val listener: SearchRefreshListener
): RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
        val itemTotalCount = recyclerView.adapter!!.itemCount - 1

        if (lastVisibleItemPosition == itemTotalCount) {
            listener.showProgress()
            viewModel.requestSearchRepositories(
                viewModel.searchText.value.toString(),
                itemTotalCount/29 + 1
            )
        }
    }
}