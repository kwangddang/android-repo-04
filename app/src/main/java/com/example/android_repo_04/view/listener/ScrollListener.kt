package com.example.android_repo_04.view.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollListener(
    private val listener: RefreshListener,
    private val requestNextPage: (Int) -> Unit
): RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
        val itemTotalCount = recyclerView.adapter!!.itemCount - 1

        if (lastVisibleItemPosition == itemTotalCount && itemTotalCount >= 0) {
            listener.showProgress()
            requestNextPage(itemTotalCount/29 + 1)
        }
    }
}