package com.example.android_repo_04.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.search.SearchItem
import com.example.android_repo_04.databinding.ItemSearchBinding

class SearchAdapter(private val viewModel: ViewModel): ListAdapter<SearchItem, SearchViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<SearchItem>() {
            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) =
                oldItem.id == newItem.id
        }
    }
}

class SearchViewHolder(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(searchItem: SearchItem, viewModel: ViewModel) {
        binding.searchItem = searchItem
        binding.vm = viewModel
    }
}