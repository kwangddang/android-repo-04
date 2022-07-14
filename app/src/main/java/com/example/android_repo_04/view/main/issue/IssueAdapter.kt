package com.example.android_repo_04.view.main.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.data.dto.issue.IssueItem
import com.example.android_repo_04.databinding.ItemIssueBinding

class IssueAdapter: RecyclerView.Adapter<IssueViewHolder>() {

    var issue: Issue = Issue()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(ItemIssueBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issue[position])
    }

    override fun getItemCount(): Int = issue.size

}

class IssueViewHolder(private val binding: ItemIssueBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(issueItem: IssueItem){
        binding.issue = issueItem
    }
}