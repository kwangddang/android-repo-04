package com.example.android_repo_04.view.main.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.databinding.ItemIssueBinding

class IssueAdapter: ListAdapter<Issue, IssueViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(ItemIssueBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun replaceItem(item: List<Issue>) {
        submitList(item)
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Issue>() {
            override fun areContentsTheSame(oldItem: Issue, newItem: Issue) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Issue, newItem: Issue) =
                oldItem.id == newItem.id
        }
    }
}

class IssueViewHolder(private val binding: ItemIssueBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(issueItem: Issue){
        binding.issue = issueItem
    }
}