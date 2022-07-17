package com.example.android_repo_04.view.main.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.issue.IssueItem
import com.example.android_repo_04.databinding.ItemIssueBinding

class IssueAdapter: ListAdapter<IssueItem, IssueViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(ItemIssueBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun replaceItem(item: Issue) {
        submitList(item)
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<IssueItem>() {
            override fun areContentsTheSame(oldItem: IssueItem, newItem: IssueItem) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: IssueItem, newItem: IssueItem) =
                oldItem.id == newItem.id
        }
    }
}

class IssueViewHolder(private val binding: ItemIssueBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(issueItem: IssueItem){
        binding.issue = issueItem
    }
}